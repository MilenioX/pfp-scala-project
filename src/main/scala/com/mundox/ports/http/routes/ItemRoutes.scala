package com.mundox.ports.http.routes

import com.mundox.core.domain.brand.BrandParam
import com.mundox.core.services.Items
import cats.Monad
import org.http4s._
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router

final case class ItemRoutes[F[_]: Monad](items: Items[F]) extends Http4sDsl[F] {
  private[routes] val prefixPath = "/items"

  implicit val brandQueryParamDecoder: QueryParamDecoder[BrandParam] = ???

  object BrandQueryParam extends OptionalQueryParamDecoderMatcher[BrandParam]("brand")

  private val httpRoutes: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root :? BrandQueryParam(brand) =>
      Ok(brand.fold(items.findAll)(b => items.findBy(b.toDomain)))
  }

  val routes: HttpRoutes[F] = Router(
    prefixPath -> httpRoutes
  )
}
