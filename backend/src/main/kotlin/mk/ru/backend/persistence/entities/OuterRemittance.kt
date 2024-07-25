package mk.ru.backend.persistence.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID
import mk.ru.backend.enums.OuterRemittanceType
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UuidGenerator

@Entity
data class OuterRemittance(
    @Id
    @UuidGenerator
    var id: UUID? = null,
    @Column(nullable = false)
    var amount: BigDecimal? = BigDecimal.ZERO,
    @CreationTimestamp
    @Column(nullable = false)
    var date: LocalDateTime? = LocalDateTime.now(),
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var type: OuterRemittanceType? = null,
    @ManyToOne(targetEntity = Wallet::class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    var wallet: Wallet? = null
)
