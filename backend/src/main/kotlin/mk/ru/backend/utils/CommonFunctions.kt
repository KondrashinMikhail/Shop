package mk.ru.backend.utils

import jakarta.persistence.criteria.Predicate
import java.math.BigDecimal
import mk.ru.backend.enums.PriceLevel
import mk.ru.backend.persistence.entities.Category
import mk.ru.backend.persistence.entities.Product
import mk.ru.backend.services.criteria.conditions.Condition
import org.springframework.data.jpa.domain.Specification

object CommonFunctions {
    fun getActualPrice(product: Product): BigDecimal =
        product.priceHistory!!.sortedBy { it.date }.reversed().first().price!!

    fun findAverage(numbers: List<BigDecimal>): BigDecimal =
        numbers.reduce { x, y -> x.plus(y) }.divide(BigDecimal(numbers.size.toString()))

    fun calculatePriceLevel(product: Product): PriceLevel {
        val actualPrice: BigDecimal = getActualPrice(product)
        val category: Category = product.category!!

        return if (actualPrice > category.maxAveragePrice && actualPrice <= category.maxPrice) PriceLevel.HIGH
        else if (actualPrice >= category.minPrice && actualPrice < category.minAveragePrice) PriceLevel.LOW
        else PriceLevel.AVERAGE
    }

    fun <T> createSpecification(conditions: List<Condition<Any>>?): Specification<T> =
        Specification<T> { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()
            conditions?.forEach { condition ->
                predicates.add(
                    condition.operation.getPredicate(
                        predicateSpecification = condition.predicateSpecification,
                        expression = root.get(condition.field),
                        value = condition.value,
                        criteriaBuilder = criteriaBuilder
                    )
                )
            }
            criteriaBuilder.and(* predicates.toTypedArray())
        }
}