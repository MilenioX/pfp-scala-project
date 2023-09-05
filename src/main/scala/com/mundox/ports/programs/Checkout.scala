package com.mundox.ports.programs

import cats.MonadThrow
import cats.syntax.all._
import cats.data.NonEmptyList
import com.mundox.core.domain.cart.{CartItem, CartTotal}
import com.mundox.core.domain.checkout.Card
import com.mundox.core.domain.payment.Payment
import com.mundox.core.domain.order.{EmptyCartError, OrderId, PaymentError, PaymentId}
import com.mundox.core.domain.user.UserId
import com.mundox.core.services.{Orders, PaymentClient, ShoppingCart}
import com.mundox.ports.effects.Background
import com.mundox.ports.retry.{Retriable, Retry}
import org.typelevel.log4cats.Logger
import retry.RetryPolicy
import squants.market.Money

import scala.concurrent.duration._


final case class Checkout[F[_]: Background: Logger: MonadThrow: Retry](
                                payments: PaymentClient[F],
                                cart: ShoppingCart[F],
                                orders: Orders[F],
                                policy: RetryPolicy[F]
                                ) {

  private def processPayment(in: Payment): F[PaymentId] = {
    Retry[F]
      .retry(policy, Retriable.Payments)(payments.process(in))
      .adaptError {
        case e =>
          PaymentError(
            Option(e.getMessage).getOrElse("Unknown")
          )
      }
  }

  def createOrder(userId: UserId, paymentId: PaymentId, items: NonEmptyList[CartItem], total: Money): F[OrderId] = {
    val action = Retry[F].retry(policy, Retriable.Orders)(orders.create(userId, paymentId, items, total))

    def bgAction(fa: F[OrderId]): F[OrderId] =
      fa.onError {
        case _ =>
          Logger[F].error(s"Failed to create order for: ${paymentId.show}"
          ) *>
            Background[F].schedule(bgAction(fa), 1.hour)
      }

    bgAction(action)
  }


  private def ensureNonEmpty[A](xs: List[A]): F[NonEmptyList[A]] =
    MonadThrow[F].fromOption(NonEmptyList.fromList(xs), EmptyCartError)

  def process(userId: UserId, card: Card): F[OrderId] = {
    cart.get(userId).flatMap {
      case CartTotal(items, total) =>
        for {
          its <- ensureNonEmpty(items)
          pid <- payments.process(Payment(userId, total, card))
          oid <- orders.create(userId, pid, its, total)
          _ <- cart.delete(userId).attempt.void
        } yield oid
    }
  }
}
