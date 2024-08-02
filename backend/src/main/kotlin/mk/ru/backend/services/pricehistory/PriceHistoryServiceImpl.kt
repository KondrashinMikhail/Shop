package mk.ru.backend.services.pricehistory

import java.util.*
import mk.ru.backend.mappers.PriceHistoryMapper
import mk.ru.backend.persistence.entities.PriceHistory
import mk.ru.backend.persistence.entities.Product
import mk.ru.backend.persistence.repositories.PriceHistoryRepo
import mk.ru.backend.services.criteria.conditions.Condition
import mk.ru.backend.utils.CommonFunctions
import mk.ru.backend.web.requests.PriceHistoryCreateRequest
import mk.ru.backend.web.responses.pricehistory.PriceHistoryInfoResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service

@Service
class PriceHistoryServiceImpl(
    private val priceHistoryRepo: PriceHistoryRepo,
    private val priceHistoryMapper: PriceHistoryMapper
) : PriceHistoryService {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass.name)

    override fun create(priceHistoryCreateRequest: PriceHistoryCreateRequest): PriceHistory {
        val savedPriceHistory: PriceHistory =
            priceHistoryRepo.save(priceHistoryMapper.toEntity(priceHistoryCreateRequest))

        log.info(
            "Created price history with id - ${savedPriceHistory.id} " +
                    "for product with id - ${priceHistoryCreateRequest.product.id} " +
                    "by user with id - ${priceHistoryCreateRequest.appUser.login}"
        )
        return savedPriceHistory
    }

    override fun searchPriceHistory(
        productId: UUID,
        conditions: List<Condition<Any>>?,
        pageable: Pageable?
    ): Page<PriceHistoryInfoResponse> {
        val specification: Specification<PriceHistory> = CommonFunctions.getSpecification(conditions)

        val additionalSpec: Specification<PriceHistory> = Specification<PriceHistory> { root, _, criteriaBuilder ->
            criteriaBuilder.equal(root.join<PriceHistory, Product>("product").get<UUID>("id"), productId)
        }

        return priceHistoryRepo.findAll(specification.and(additionalSpec), pageable ?: Pageable.unpaged())
            .map { priceHistoryMapper.toInfoResponse(it) }
    }
}