package com.mundox.core.services

import com.mundox.core.domain.category._

trait Categories [F[_]] {
  def findAll: F[List[Category]]
  def create(name: CategoryName): F[CategoryId]
}
