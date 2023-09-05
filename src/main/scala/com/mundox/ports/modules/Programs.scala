package com.mundox.ports.modules

import cats.effect.Temporal
import cats.implicits.catsSyntaxSemigroup
import org.typelevel.log4cats.Logger
import retry.RetryPolicies.{exponentialBackoff, limitRetries}
import retry.RetryPolicy

sealed abstract class Programs[F[_]: Logger: Temporal] () {

  val retryPolicy: RetryPolicy[F] =
    limitRetries[F](cfg.retriesLimit.value) |+| exponentialBackoff[F](cfg.retriesBackoff)

}
