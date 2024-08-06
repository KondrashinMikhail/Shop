package mk.ru.backend.criteria.conditions

import mk.ru.backend.enums.CriteriaOperation
import mk.ru.backend.criteria.specifications.StringPredicateSpecification

data class StringCondition(
    override val field: String,
    override val operation: CriteriaOperation,
    override val value: String,
) : Condition<String>(
    field = field,
    operation = operation,
    value = value,
    predicateSpecification = StringPredicateSpecification()
)
