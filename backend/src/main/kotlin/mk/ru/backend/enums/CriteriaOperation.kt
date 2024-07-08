package mk.ru.backend.enums

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Predicate
import mk.ru.backend.services.criteria.specifications.PredicateSpecification

enum class CriteriaOperation(
    private val criteriaOperation: (PredicateSpecification<Any>, Expression<Any>, Any, CriteriaBuilder) -> Predicate
) {
    @JsonAlias("=", "equals")
    EQUALS(PredicateSpecification<Any>::equalPredicate),

    @JsonAlias("!=", "not_equals")
    NOT_EQUALS(PredicateSpecification<Any>::notEqualPredicate),

    @JsonAlias(">", "greater_than")
    GREATER_THAN(PredicateSpecification<Any>::greaterThanPredicate),

    @JsonAlias(">=", "greater_than_or_equal")
    GREATER_THAN_OR_EQUAL(PredicateSpecification<Any>::greaterThanOrEqualPredicate),

    @JsonAlias("<", "less_than")
    LESS_THAN(PredicateSpecification<Any>::lessThanPredicate),

    @JsonAlias("<=", "less_than_or_equal")
    LESS_THAN_OR_EQUAL(PredicateSpecification<Any>::lessThanOrEqualPredicate),

    @JsonAlias("~", "like")
    LIKE(PredicateSpecification<Any>::likePredicate);

    fun getPredicate(
        predicateSpecification: PredicateSpecification<Any>,
        expression: Expression<Any>,
        value: Any,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return criteriaOperation(predicateSpecification, expression, value, criteriaBuilder)
    }
}
