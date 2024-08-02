package mk.ru.backend.web.requests.pricehistory

import java.math.BigDecimal
import mk.ru.backend.persistence.entities.AppUser
import mk.ru.backend.persistence.entities.Product

data class PriceHistoryCreateRequest(
    val product: Product,
    val appUser: AppUser,
    val price: BigDecimal
)