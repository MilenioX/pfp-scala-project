package com.mundox.core.domain

import io.estatico.newtype.macros.newtype

import java.util.UUID

object user {

  @newtype case class UserId(uuid: UUID)
  @newtype case class UserName(value: String)
  @newtype case class Password(value: String)
  @newtype case class EncryptedPassword(value: String)


  case class User(id: UserId, name: UserName)
  case class UserWithPassword(
                             id: UserId,
                             name: UserName,
                             password: EncryptedPassword
                             )
}
