package mk.ru.backend.services.criteria.specifications

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Predicate

class BooleanPredicateSpecification : PredicateSpecification<Boolean> {
    override fun equalPredicate(
        expression: Expression<Boolean>,
        value: Boolean,
        criteriaBuilder: CriteriaBuilder
    ): Predicate = criteriaBuilder.equal(expression, value)

    override fun notEqualPredicate(
        expression: Expression<Boolean>,
        value: Boolean,
        criteriaBuilder: CriteriaBuilder
    ): Predicate = criteriaBuilder.notEqual(expression, value)


    override fun greaterThanPredicate(
        expression: Expression<Boolean>,
        value: Boolean,
        criteriaBuilder: CriteriaBuilder
    ): Predicate = criteriaBuilder.greaterThan(expression, value)


    override fun greaterThanOrEqualPredicate(
        expression: Expression<Boolean>,
        value: Boolean,
        criteriaBuilder: CriteriaBuilder
    ): Predicate = criteriaBuilder.greaterThanOrEqualTo(expression, value)


    override fun lessThanPredicate(
        expression: Expression<Boolean>,
        value: Boolean,
        criteriaBuilder: CriteriaBuilder
    ): Predicate =
        criteriaBuilder.lessThan(expression, value)


    override fun lessThanOrEqualPredicate(
        expression: Expression<Boolean>,
        value: Boolean,
        criteriaBuilder: CriteriaBuilder
    ): Predicate =
        criteriaBuilder.lessThanOrEqualTo(expression, value)


    override fun likePredicate(
        expression: Expression<Boolean>,
        value: Boolean,
        criteriaBuilder: CriteriaBuilder
    ): Predicate = criteriaBuilder.equal(expression, value)
}
    
