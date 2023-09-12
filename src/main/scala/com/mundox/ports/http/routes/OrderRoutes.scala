package com.mundox.ports.http.routes

import cats.Monad
import com.mundox.core.domain.user.CommonUser
import com.mundox.core.services.Orders
import com.mundox.ports.http.domain.vars.OrderIdVar
import org.http4s.{AuthedRoutes, HttpRoutes}
import org.http4s.dsl.Http4sDsl
import org.http4s.server.{AuthMiddleware, Router}

final case class OrderRoutes[F[_]: Monad](orders: Orders[F]) extends Http4sDsl[F] {

  private[routes] val prefixPath = "/orders"

  private val httpRoutes: AuthedRoutes[CommonUser, F] =
    AuthedRoutes.of {
      case GET -> Root as user =>
        Ok(orders.findBy(user.value.id))
      case GET -> Root / OrderIdVar(orderId) as user =>
        Ok(orders.get(user.value.id, orderId))
    }

  def routes(authMiddleware: AuthMiddleware[F, CommonUser]): HttpRoutes[F] = Router(
    prefixPath -> authMiddleware(httpRoutes)
  )
}
