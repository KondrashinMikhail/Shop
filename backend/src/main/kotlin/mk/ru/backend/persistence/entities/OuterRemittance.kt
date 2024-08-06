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
    @Column(nullable = false, updatable = false, insertable = false)
    var amount: BigDecimal,
    @Column(nullable = false, updatable = false, insertable = false)
    @CreationTimestamp
    var date: LocalDateTime,
    @Column(nullable = false, updatable = false, insertable = false)
    @Enumerated(EnumType.STRING)
    var type: OuterRemittanceType,
    @ManyToOne(targetEntity = Wallet::class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    var wallet: Wallet
)
