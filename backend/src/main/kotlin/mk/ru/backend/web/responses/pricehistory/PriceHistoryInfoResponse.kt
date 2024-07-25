package mk.ru.backend.web.responses.pricehistory

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class PriceHistoryInfoResponse(
    val id: UUID,
    val price: BigDecimal,
    val date: LocalDateTime,
    val user: PriceHistoryAppUserResponse
)
