package com.mundox.core.domain

import com.mundox.core.domain.checkout.Card
import com.mundox.core.domain.user.UserId
import squants.market.Money

object payment {

  case class Payment(
                    id: UserId,
                    total: Money,
                    card: Card
                    )
}
