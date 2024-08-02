package mk.ru.backend.services.pricehistory

import java.util.UUID
import mk.ru.backend.persistence.entities.PriceHistory
import mk.ru.backend.services.criteria.conditions.Condition
import mk.ru.backend.web.requests.pricehistory.PriceHistoryCreateRequest
import mk.ru.backend.web.responses.pricehistory.PriceHistoryInfoResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PriceHistoryService {
    fun create(priceHistoryCreateRequest: PriceHistoryCreateRequest): PriceHistory
    fun searchPriceHistory(productId: UUID, conditions: List<Condition<Any>>?, pageable: Pageable?): Page<PriceHistoryInfoResponse>
}