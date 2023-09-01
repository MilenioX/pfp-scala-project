package com.mundox.core.services

import com.mundox.core.domain.auth.JwtToken
import com.mundox.core.domain.user.{Password, User, UserName}

trait Auth[F[_]] {

  def findUser(token: JwtToken): F[Option[User]]
  def newUser(username: UserName, password: Password): F[JwtToken]
  def login(userName: UserName, password: Password): F[JwtToken]
  def logout(token: JwtToken, userName: UserName): F[Unit]
}
