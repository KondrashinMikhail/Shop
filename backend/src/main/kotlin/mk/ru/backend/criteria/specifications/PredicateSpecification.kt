package mk.ru.backend.criteria.specifications

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Predicate

interface PredicateSpecification<T> {
    fun equalPredicate(expression: Expression<T>, value: T, criteriaBuilder: CriteriaBuilder): Predicate
    fun notEqualPredicate(expression: Expression<T>, value: T, criteriaBuilder: CriteriaBuilder): Predicate
    fun greaterThanPredicate(expression: Expression<T>, value: T, criteriaBuilder: CriteriaBuilder): Predicate
    fun greaterThanOrEqualPredicate(expression: Expression<T>, value: T, criteriaBuilder: CriteriaBuilder): Predicate
    fun lessThanPredicate(expression: Expression<T>, value: T, criteriaBuilder: CriteriaBuilder): Predicate
    fun lessThanOrEqualPredicate(expression: Expression<T>, value: T, criteriaBuilder: CriteriaBuilder): Predicate
    fun likePredicate(expression: Expression<T>, value: T, criteriaBuilder: CriteriaBuilder): Predicate
}