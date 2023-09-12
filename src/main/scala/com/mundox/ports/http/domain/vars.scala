package com.mundox.ports.http.domain

import java.util.UUID
import com.mundox.core.domain.item.ItemId
import com.mundox.core.domain.order.OrderId

import cats.implicits._

object vars {

  object ItemIdVar {
    def unapply(str: String): Option[ItemId] =
      Either.catchNonFatal(ItemId(UUID.fromString(str))).toOption
  }

  object OrderIdVar {
    def unapply(str: String): Option[OrderId] =
      Either.catchNonFatal(OrderId(UUID.fromString(str))).toOption
  }
}
