package com.mundox.core.services

import cats.data.NonEmptyList
import com.mundox.core.domain.order.{Order, OrderId, PaymentId}
import com.mundox.core.domain.cart.CartItem
import com.mundox.core.domain.user.UserId
import squants.market.Money

trait Orders[F[_]] {
  def get(
           userId: UserId,
           orderId: OrderId
         )

  def findBy(userId: UserId): F[List[Order]]
  def create(
            userId: UserId,
            paymentId: PaymentId,
            items: NonEmptyList[CartItem],
            total: Money
            ): F[OrderId]

}
