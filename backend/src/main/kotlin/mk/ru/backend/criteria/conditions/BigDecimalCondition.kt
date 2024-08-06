package mk.ru.backend.criteria.conditions

import java.math.BigDecimal
import mk.ru.backend.enums.CriteriaOperation
import mk.ru.backend.criteria.specifications.BigDecimalPredicateSpecification

data class BigDecimalCondition(
    override val field: String,
    override val operation: CriteriaOperation,
    override val value: BigDecimal,
) : Condition<BigDecimal>(
    field = field,
    operation = operation,
    value = value,
    predicateSpecification = BigDecimalPredicateSpecification()
)