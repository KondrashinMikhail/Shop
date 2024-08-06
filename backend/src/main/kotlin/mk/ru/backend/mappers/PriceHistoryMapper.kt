package mk.ru.backend.mappers

import java.time.LocalDateTime
import mk.ru.backend.persistence.entities.PriceHistory
import mk.ru.backend.web.requests.pricehistory.PriceHistoryCreateRequest
import mk.ru.backend.web.responses.pricehistory.PriceHistoryInfoResponse
import org.springframework.stereotype.Component

@Component
class PriceHistoryMapper(private val appUserMapper: AppUserMapper) {
    fun toCreateEntity(createRequest: PriceHistoryCreateRequest): PriceHistory = PriceHistory(
        product = createRequest.product,
        appUser = createRequest.appUser,
        price = createRequest.price,
        date = LocalDateTime.now(),
    )

    fun toInfoResponse(priceHistory: PriceHistory): PriceHistoryInfoResponse = PriceHistoryInfoResponse(
        id = priceHistory.id!!,
        price = priceHistory.price,
        date = priceHistory.date,
        user = appUserMapper.toPriceHistoryResponse(priceHistory.appUser)
    )
}