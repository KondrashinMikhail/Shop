package mk.ru.backend.services.criteria.conditions

import mk.ru.backend.enums.CriteriaOperation
import mk.ru.backend.services.criteria.specifications.BooleanPredicateSpecification

data class BooleanCondition(
    override val field: String,
    override val operation: CriteriaOperation,
    override val value: Boolean
) : Condition<Boolean>(
    field = field,
    operation = operation,
    value = value,
    predicateSpecification = BooleanPredicateSpecification()
)
