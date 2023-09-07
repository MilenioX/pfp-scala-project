package com.mundox.ports.ext.http4s

import cats.implicits._
import eu.timepit.refined.api.{Refined, Validate}
import eu.timepit.refined.refineV
import org.http4s.{ParseFailure, QueryParamDecoder}


object refined {
  implicit def refinedParamDecoder[T: QueryParamDecoder, P](implicit ev: Validate[T, P]):QueryParamDecoder[T Refined P] =
    QueryParamDecoder[T].emap(refineV[P](_).leftMap(m => ParseFailure(m, m)))
}
