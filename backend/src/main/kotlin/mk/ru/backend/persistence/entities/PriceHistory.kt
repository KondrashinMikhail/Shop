package mk.ru.backend.persistence.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UuidGenerator

@Entity
data class PriceHistory(
    @Id
    @UuidGenerator
    var id: UUID? = null,
    @Column(nullable = false, updatable = false, insertable = false)
    var price: BigDecimal,
    @Column(nullable = false, updatable = false, insertable = false)
    @CreationTimestamp
    var date: LocalDateTime,
    @ManyToOne(targetEntity = Product::class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    var product: Product,
    @ManyToOne(targetEntity = AppUser::class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    var appUser: AppUser
)
