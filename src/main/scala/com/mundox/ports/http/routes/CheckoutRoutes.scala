package com.mundox.ports.http.routes

import cats.MonadThrow
import com.mundox.core.domain.checkout.Card
import com.mundox.core.domain.cart.CartNotFound
import com.mundox.core.domain.user.CommonUser
import com.mundox.ports.programs.Checkout
import cats.syntax.all._
import com.mundox.core.domain.order.{EmptyCartError, OrderOrPaymentError}
import com.mundox.ports.ext.http4s.refined.RefinedRequestDecoder
import org.http4s._
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.circe.JsonDecoder
import org.http4s.dsl.Http4sDsl
import org.http4s.server._

final case class CheckoutRoutes[F[_]: JsonDecoder : MonadThrow](checkout: Checkout[F]) extends Http4sDsl[F] {

  private[routes] val prefixPath = "/checkout"

  private val httpRoutes: AuthedRoutes[CommonUser, F] =
    AuthedRoutes.of {
      case ar @ POST -> Root as user =>
        ar.req.decodeR[Card] { card =>
          checkout.process(user.value.id, card)
            .flatMap(Created(_))
            .recoverWith {
              case CartNotFound(userId) =>
                NotFound(s"Cart not found for user: ${userId.uuid}")
              case EmptyCartError =>
                BadRequest("Shopping cart is empty!")
              case e: OrderOrPaymentError =>
                BadRequest(e.show)
            }
        }
    }

  def routes(authMiddleware: AuthMiddleware[F, CommonUser]): HttpRoutes[F] = Router(
    prefixPath -> authMiddleware(httpRoutes)
  )
}
