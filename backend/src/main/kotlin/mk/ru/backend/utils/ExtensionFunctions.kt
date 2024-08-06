package mk.ru.backend.utils

import jakarta.persistence.criteria.Predicate
import java.math.BigDecimal
import mk.ru.backend.enums.PriceLevel
import mk.ru.backend.persistence.entities.Category
import mk.ru.backend.persistence.entities.Product
import mk.ru.backend.criteria.conditions.Condition
import org.springframework.data.jpa.domain.Specification

object ExtensionFunctions {
    fun Product.getActualPrice(): BigDecimal =
        priceHistory.sortedBy { it.date }.reversed().first().price

    fun Product.getPriceLevel(): PriceLevel {
        val actualPrice: BigDecimal = getActualPrice()
        val category: Category = category

        return if (actualPrice > category.maxAveragePrice && actualPrice <= category.maxPrice) PriceLevel.HIGH
        else if (actualPrice >= category.minPrice && actualPrice < category.minAveragePrice) PriceLevel.LOW
        else PriceLevel.AVERAGE
    }

    fun BigDecimal.getPercent(percentAmount: BigDecimal): BigDecimal = multiply(percentAmount.divide(BigDecimal(100)))

    fun String.isPatternFits(pattern: String): Boolean = Regex(pattern).matches(this)

    fun getAverage(vararg numbers: BigDecimal?): BigDecimal? = numbers.reduce { x, y ->
        if (x == null || y == null) return null
        x.plus(y)
    }?.divide(BigDecimal(numbers.size.toString()))

    fun <T> getSpecification(conditions: List<Condition<Any>>?): Specification<T> =
        Specification<T> { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()
            conditions?.forEach { condition ->
                predicates += condition.operation.getPredicate(
                    predicateSpecification = condition.predicateSpecification,
                    expression = root.get(condition.field),
                    value = condition.value,
                    criteriaBuilder = criteriaBuilder
                )
            }
            criteriaBuilder.and(* predicates.toTypedArray())
        }
}