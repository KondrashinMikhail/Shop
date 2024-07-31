package mk.ru.backend.persistence.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.math.BigDecimal

@Entity
data class Category(
    @Id
    var name: String? = null,
    @Column(nullable = false)
    var minPrice: BigDecimal = BigDecimal.ZERO,
    @Column(nullable = false)
    var maxPrice: BigDecimal = BigDecimal.ZERO,
    @Column(nullable = false)
    var averagePrice: BigDecimal = BigDecimal.ZERO,
    @Column(nullable = false)
    var minAveragePrice: BigDecimal = BigDecimal.ZERO,
    @Column(nullable = false)
    var maxAveragePrice: BigDecimal = BigDecimal.ZERO,
    @OneToMany(targetEntity = Product::class, mappedBy = "category")
    var products: List<Product>? = null
)
