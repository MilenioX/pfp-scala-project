package com.mundox.core.domain

import com.mundox.core.domain.user.UserName

import scala.util.control.NoStackTrace

object auth {

  case class UserNotFound(username: UserName) extends NoStackTrace
  case class UserNameInUse(username: UserName)   extends NoStackTrace
  case class InvalidPassword(username: UserName) extends NoStackTrace


}
