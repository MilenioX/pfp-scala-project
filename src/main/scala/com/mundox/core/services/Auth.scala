package com.mundox.core.services

import com.mundox.core.domain.user.{Password, User, UserName}
import dev.profunktor.auth.jwt.JwtToken

trait Auth[F[_]] {

  def findUser(token: JwtToken): F[Option[User]]
  def newUser(username: UserName, password: Password): F[JwtToken]
  def login(userName: UserName, password: Password): F[JwtToken]
  def logout(token: JwtToken, userName: UserName): F[Unit]
}
