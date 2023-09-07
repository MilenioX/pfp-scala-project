package com.mundox.core.services

import com.mundox.core.domain.healthCheck.AppStatus

trait HealthCheck[F[_]] {

  def status: F[AppStatus]
}
