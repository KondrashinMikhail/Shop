package mk.ru.backend.criteria.specifications

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Predicate
import java.math.BigDecimal

class BigDecimalPredicateSpecification : PredicateSpecification<BigDecimal> {
    override fun equalPredicate(
        expression: Expression<BigDecimal>,
        value: BigDecimal,
        criteriaBuilder: CriteriaBuilder
    ): Predicate = criteriaBuilder.equal(expression, value)

    override fun notEqualPredicate(
        expression: Expression<BigDecimal>,
        value: BigDecimal,
        criteriaBuilder: CriteriaBuilder
    ): Predicate = criteriaBuilder.notEqual(expression, value)

    override fun greaterThanPredicate(
        expression: Expression<BigDecimal>,
        value: BigDecimal,
        criteriaBuilder: CriteriaBuilder
    ): Predicate = criteriaBuilder.greaterThan(expression, value)

    override fun greaterThanOrEqualPredicate(
        expression: Expression<BigDecimal>,
        value: BigDecimal,
        criteriaBuilder: CriteriaBuilder
    ): Predicate = criteriaBuilder.greaterThanOrEqualTo(expression, value)

    override fun lessThanPredicate(
        expression: Expression<BigDecimal>,
        value: BigDecimal,
        criteriaBuilder: CriteriaBuilder
    ): Predicate = criteriaBuilder.lessThan(expression, value)

    override fun lessThanOrEqualPredicate(
        expression: Expression<BigDecimal>,
        value: BigDecimal,
        criteriaBuilder: CriteriaBuilder
    ): Predicate = criteriaBuilder.lessThanOrEqualTo(expression, value)

    override fun likePredicate(
        expression: Expression<BigDecimal>,
        value: BigDecimal,
        criteriaBuilder: CriteriaBuilder
    ): Predicate = criteriaBuilder.between(
        expression,
        value.multiply(BigDecimal.valueOf(0.9)),
        value.multiply(BigDecimal.valueOf(1.1))
    )
}