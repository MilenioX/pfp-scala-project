package com.mundox.core.services

import com.mundox.core.domain.brand.BrandName
import com.mundox.core.domain.item.{CreateItem, Item, ItemId, UpdateItem}

trait Items[F[_]] {
  def findAll: F[List[Item]]
  def findBy(brand: BrandName): F[List[Item]]
  def findById(itemId: ItemId): F[Option[Item]]
  def create(item: CreateItem): F[ItemId]
  def update(item: UpdateItem): F[Unit]

}
