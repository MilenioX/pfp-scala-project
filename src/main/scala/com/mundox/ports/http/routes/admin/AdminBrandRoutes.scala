package com.mundox.ports.http.routes.admin

import cats.MonadThrow
import cats.implicits.toFlatMapOps
import com.mundox.core.domain.brand.BrandParam
import com.mundox.core.domain.user.AdminUser
import com.mundox.core.services.Brands
import com.mundox.ports.ext.http4s.refined.RefinedRequestDecoder
import io.circe.JsonObject
import io.circe.syntax._
import org.http4s.{AuthedRoutes, HttpRoutes}
import org.http4s.circe.JsonDecoder
import org.http4s.dsl.Http4sDsl
import org.http4s.server.{AuthMiddleware, Router}

final case class AdminBrandRoutes[F[_]: JsonDecoder : MonadThrow](brands: Brands[F]) extends Http4sDsl[F] {
  private[admin] val prefixPath = "/brands"

  private val httpRoutes: AuthedRoutes[AdminUser, F] =
    AuthedRoutes.of{
      case ar @ POST -> Root as _ =>
        ar.req.decodeR[BrandParam] {bp =>
          brands.create(bp.toDomain).flatMap { id =>
            Created(JsonObject.singleton("brand_id", id.asJson))
          }
        }
    }

  def routes(authMiddleware: AuthMiddleware[F, AdminUser]): HttpRoutes[F] = Router(
    prefixPath -> authMiddleware(httpRoutes)
  )
}
