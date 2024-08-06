package mk.ru.backend.persistence.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.hibernate.annotations.UuidGenerator

@Entity
data class Transaction(
    @Id
    @UuidGenerator
    var id: UUID? = null,
    @Column(nullable = false, updatable = false, insertable = false)
    var amount: BigDecimal,
    @Column(nullable = false, updatable = false, insertable = false)
    var feeAmount: BigDecimal,
    @Column(nullable = false,  updatable = false, insertable = false)
    var feePercent: BigDecimal,
    @Column(nullable = false,  updatable = false, insertable = false)
    @CreationTimestamp
    var date: LocalDateTime,
    @ManyToOne(targetEntity = Wallet::class)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    var sender: Wallet,
    @ManyToOne(targetEntity = Wallet::class)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(nullable = false,  updatable = false, insertable = false)
    var recipient: Wallet,
    @OneToOne(targetEntity = Product::class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,  updatable = false, insertable = false)
    var product: Product
)
