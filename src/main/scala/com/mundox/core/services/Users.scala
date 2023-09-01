package com.mundox.core.services

import com.mundox.core.domain.user.{EncryptedPassword, UserId, UserName, UserWithPassword}

trait Users[F[_]] {
  def find(username: UserName): F[Option[UserWithPassword]]
  def create(userName: UserName, password: EncryptedPassword): F[UserId]
}
