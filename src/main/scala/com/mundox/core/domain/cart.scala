package com.mundox.core.domain

import com.mundox.core.domain.item.{Item, ItemId}
import com.mundox.core.domain.user.UserId
import io.estatico.newtype.macros.newtype
import squants.market.Money

import scala.util.control.NoStackTrace

object cart {

  @newtype case class Quantity(value: Int)
  @newtype case class Cart(items: Map[ItemId, Quantity])

  case class CartItem(item: Item, quantity: Quantity)
  case class CartTotal(items: List[CartItem], total: Money)

  case class CartNotFound(userId: UserId) extends NoStackTrace
}
