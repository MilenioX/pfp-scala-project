package com.mundox.core.domain

import com.mundox.core.domain.item.ItemId
import com.mundox.core.domain.cart.Quantity
import derevo.cats.show
import derevo.derive
import io.estatico.newtype.macros.newtype
import squants.market.Money

import java.util.UUID
import scala.util.control.NoStackTrace

object order {
  @newtype case class OrderId(uuid: UUID)

  @derive(show)
  @newtype case class PaymentId(uuid: UUID)

  case class Order(
                  id: OrderId,
                  pid: PaymentId,
                  items: Map[ItemId, Quantity],
                  total: Money
                  )

  case object EmptyCartError extends NoStackTrace

  sealed trait OrderOrPaymentError extends NoStackTrace {
    def cause: String
  }

  case class PaymentError(cause: String) extends OrderOrPaymentError
}
