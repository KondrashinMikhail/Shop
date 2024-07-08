package mk.ru.backend.services.criteria.conditions

import mk.ru.backend.enums.CriteriaOperation
import mk.ru.backend.services.criteria.specifications.StringPredicateSpecification

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
