package com.mundox.core.domain

import com.mundox.core.domain.item.ItemId
import com.mundox.core.domain.cart.Quantity
import io.estatico.newtype.macros.newtype
import squants.market.Money

import java.util.UUID

object order {
  @newtype case class OrderId(uuid: UUID)
  @newtype case class PaymentId(uuid: UUID)

  case class Order(
                  id: OrderId,
                  pid: PaymentId,
                  items: Map[ItemId, Quantity],
                  total: Money
                  )
}
