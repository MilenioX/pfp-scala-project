package com.mundox.core.domain

import com.mundox.core.domain.item.{Item, ItemId}
import io.estatico.newtype.macros.newtype
import squants.market.Money

object cart {

  @newtype case class Quantity(value: Int)
  @newtype case class Cart(items: Map[ItemId, Quantity])

  case class CartItem(item: Item, quantity: Quantity)
  case class CartTotal(items: List[CartItem], total: Money)
}
