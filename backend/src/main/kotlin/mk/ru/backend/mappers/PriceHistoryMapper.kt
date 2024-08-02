package mk.ru.backend.mappers

import mk.ru.backend.persistence.entities.PriceHistory
import mk.ru.backend.web.requests.pricehistory.PriceHistoryCreateRequest
import mk.ru.backend.web.responses.pricehistory.PriceHistoryInfoResponse
import org.springframework.stereotype.Component

@Component
class PriceHistoryMapper(private val appUserMapper: AppUserMapper) {
    fun toEntity(createRequest: PriceHistoryCreateRequest): PriceHistory = PriceHistory(
        product = createRequest.product,
        appUser = createRequest.appUser,
        price = createRequest.price,
    )

    fun toInfoResponse(priceHistory: PriceHistory): PriceHistoryInfoResponse = PriceHistoryInfoResponse(
        id = priceHistory.id!!,
        price = priceHistory.price!!,
        date = priceHistory.date!!,
        user = appUserMapper.toPriceHistoryResponse(priceHistory.appUser!!)
    )
}