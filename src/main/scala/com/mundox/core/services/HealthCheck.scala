package com.mundox.core.services

trait HealthCheck[F[_]] {

  def status: F[AppStatus]
}
