package com.mundox.core.services

import com.mundox.core.domain.item.ItemId

trait ShoppingCart[F[_]] {
  def add(
         userId: UserId,
         itemId: ItemId,
         quantity: Quantity
         ): F[Unit]
  def get(userId: UserId): F[CartTotal]
  def delete(userId: UserId): F[Unit]
  def removeItem(userId: UserId, itemId: ItemId): F[Unit]
  def update(userId: UserId, cart: Cart): F[Unit]

}
