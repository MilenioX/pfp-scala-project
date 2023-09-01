package com.mundox.core.services

import com.mundox.core.domain.order.PaymentId
import com.mundox.core.domain.payment.Payment

trait PaymentClient[F[_]] {

  def process(payment: Payment): F[PaymentId]
}
