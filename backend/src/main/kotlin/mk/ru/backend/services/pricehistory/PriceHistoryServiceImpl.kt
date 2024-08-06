package mk.ru.backend.services.pricehistory

import jakarta.transaction.Transactional
import java.util.UUID
import mk.ru.backend.mappers.PriceHistoryMapper
import mk.ru.backend.persistence.entities.PriceHistory
import mk.ru.backend.persistence.entities.Product
import mk.ru.backend.persistence.repositories.PriceHistoryRepo
import mk.ru.backend.criteria.conditions.Condition
import mk.ru.backend.utils.ExtensionFunctions
import mk.ru.backend.web.requests.pricehistory.PriceHistoryCreateRequest
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

    @Transactional
    override fun create(priceHistoryCreateRequest: PriceHistoryCreateRequest): PriceHistory {
        val savedPriceHistory: PriceHistory =
            priceHistoryRepo.save(priceHistoryMapper.toCreateEntity(priceHistoryCreateRequest))
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
        val specification: Specification<PriceHistory> = ExtensionFunctions.getSpecification(conditions)
        val additionalSpec: Specification<PriceHistory> = Specification<PriceHistory> { root, _, criteriaBuilder ->
            criteriaBuilder.equal(root.join<PriceHistory, Product>("product").get<UUID>("id"), productId)
        }

        return priceHistoryRepo.findAll(
            specification.and(additionalSpec),
            pageable ?: Pageable.unpaged()
        ).map { priceHistoryMapper.toInfoResponse(it) }
    }
}