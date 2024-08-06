package mk.ru.backend.persistence.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.hibernate.annotations.UuidGenerator
import org.springframework.data.annotation.LastModifiedDate

@Entity
data class Wallet(
    @Id
    @UuidGenerator
    var id: UUID? = null,
    @Column(nullable = false)
    var balance: BigDecimal = BigDecimal.ZERO,
    @Column(nullable = false)
    @LastModifiedDate
    var lastModifiedDate: LocalDateTime,
    @ManyToOne(targetEntity = AppUser::class)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    var owner: AppUser,
    @OneToMany(targetEntity = Transaction::class, mappedBy = "sender")
    @Fetch(FetchMode.JOIN)
    var transactionsSender: List<Transaction>? = null,
    @OneToMany(targetEntity = Transaction::class, mappedBy = "recipient")
    @Fetch(FetchMode.JOIN)
    var transactionsRecipient: List<Transaction>? = null,
    @OneToMany(targetEntity = OuterRemittance::class, mappedBy = "wallet")
    @Fetch(FetchMode.JOIN)
    var outerRemittances: List<OuterRemittance>? = null
)
