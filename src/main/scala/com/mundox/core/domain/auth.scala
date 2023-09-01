package com.mundox.core.domain

import io.estatico.newtype.macros.newtype

object auth {

  @newtype case class JwtToken(value: String)
}
