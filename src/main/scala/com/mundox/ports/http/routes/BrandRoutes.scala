package com.mundox.ports.http.routes

import cats.Monad
import com.mundox.core.services.Brands
import org.http4s._
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router

case class BrandRoutes[F[_]: Monad](brands: Brands[F]) extends Http4sDsl[F] {
  private[routes] val prefixPath = "/brands"

  private val httpRoutes: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root =>
      Ok(brands.findAll)
  }

  val route: HttpRoutes[F] = Router(
    prefixPath -> httpRoutes
  )
}
