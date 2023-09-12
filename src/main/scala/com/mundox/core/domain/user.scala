package com.mundox.core.domain

import eu.timepit.refined.auto.autoUnwrap
import eu.timepit.refined.types.string.NonEmptyString
import io.estatico.newtype.macros.newtype

import java.util.UUID

object user {

  @newtype case class UserId(uuid: UUID)
  @newtype case class UserName(value: String)
  @newtype case class Password(value: String)
  @newtype case class EncryptedPassword(value: String)


  @newtype
  case class UserNameParam(value: NonEmptyString) {
    def toDomain: UserName = UserName(value.toLowerCase)
  }
  @newtype
  case class PasswordParam(value: NonEmptyString) {
    def toDomain: Password = Password(value)
  }

  case class CreateUser(
                         username: UserNameParam,
                         password: PasswordParam
                       )

  case class User(id: UserId, name: UserName)
  case class UserWithPassword(
                             id: UserId,
                             name: UserName,
                             password: EncryptedPassword
                             )

  @newtype
  case class CommonUser(value: User)

  @newtype
  case class AdminUser(value: User)

  case class LoginUser(
                        username: UserNameParam,
                        password: PasswordParam
                      )
}
