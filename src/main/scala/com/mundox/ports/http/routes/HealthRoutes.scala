package com.mundox.ports.http.routes

import cats.Monad
import com.mundox.core.services.HealthCheck
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router

final case class HealthRoutes[F[_]: Monad](healthCheck: HealthCheck[F]) extends Http4sDsl[F] {
  private[routes] val prefixPath = "/healthcheck"

  private val httpRoutes: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root =>
      Ok(healthCheck.status)
  }

  val routes: HttpRoutes[F] = Router(
    prefixPath -> httpRoutes
  )

}
