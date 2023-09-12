package com.mundox.core.domain

import com.mundox.core.domain.brand.{Brand, BrandId}
import com.mundox.core.domain.category.{Category, CategoryId}
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.string.{Uuid, ValidBigDecimal}
import eu.timepit.refined.types.string.NonEmptyString
import io.estatico.newtype.macros.newtype
import squants.market._

import java.util.UUID

object item {

  @newtype case class ItemId(value: UUID)
  @newtype case class ItemName(value: String)
  @newtype case class ItemDescription(value: String)

  case class Item(
                 uuid: ItemId,
                 name: ItemName,
                 description: ItemDescription,
                 price: Money,
                 brand: Brand,
                 category: Category
                 )

  @newtype
  case class ItemNameParam(value: NonEmptyString)

  @newtype
  case class ItemDescriptionParam(value: NonEmptyString)

  @newtype
  case class PriceParam(value: String Refined ValidBigDecimal)

  case class CreateItemParam(
                              name: ItemNameParam,
                              description: ItemDescriptionParam,
                              price: PriceParam,
                              brandId: BrandId,
                              categoryId: CategoryId
                            ) {
    def toDomain: CreateItem =
      CreateItem(
        ItemName(name.value),
        ItemDescription(description.value),
        USD(BigDecimal(price.value)),
        brandId,
        categoryId
      )
  }

  @newtype
  case class ItemIdParam(value: String Refined Uuid)

  case class UpdateItemParam(
                              id: ItemIdParam,
                              price: PriceParam
                            ) {
    def toDomain: UpdateItem =
      UpdateItem(
        ItemId(UUID.fromString(id.value)),
        USD(BigDecimal(price.value))
      )
  }

  case class CreateItem(
                       name: ItemName,
                       description: ItemDescription,
                       price: Money,
                       brandId: BrandId,
                       categoryId: CategoryId
                       )

  case class UpdateItem(
                       id: ItemId,
                       price: Money
                       )
}
