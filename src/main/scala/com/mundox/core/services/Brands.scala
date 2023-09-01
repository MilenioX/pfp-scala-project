package com.mundox.core.services

import com.mundox.core.domain.brand._


trait Brands[F[_]] {
  def findAll: F[List[Brand]]
  def create(name: BrandName): F[BrandId]
}
