package mk.ru.backend.criteria.conditions

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.JsonTypeInfo
import mk.ru.backend.enums.CriteriaOperation
import mk.ru.backend.criteria.specifications.PredicateSpecification

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    visible = true,
    property = "field"
)
@JsonSubTypes(
    //product
    Type(value = StringCondition::class, name = "name"),
    Type(value = StringCondition::class, name = "description"),
    Type(value = LocalDateCondition::class, name = "registrationDate"),
    Type(value = BooleanCondition::class, name = "deleted"),
    //price_history
    Type(value = BigDecimalCondition::class, name = "price"),
    Type(value = LocalDateCondition::class, name = "date"),
    //transaction
    Type(value = BigDecimalCondition::class, name = "amount"),
    Type(value = LocalDateCondition::class, name = "date"),
)
abstract class Condition<T>(
    open val field: String,
    open val operation: CriteriaOperation,
    open val value: T,
    @JsonIgnore
    val predicateSpecification: PredicateSpecification<T>
)
