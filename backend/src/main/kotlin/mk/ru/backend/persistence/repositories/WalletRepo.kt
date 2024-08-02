package mk.ru.backend.persistence.repositories

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import mk.ru.backend.persistence.entities.Wallet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WalletRepo : JpaRepository<Wallet, UUID> {
    fun findByOwnerLogin(login: String): Optional<Wallet>
    fun findAllByLastModifiedDateAfterAndBalanceNot(
        lastModifiedDate: LocalDateTime,
        balance: BigDecimal = BigDecimal.ZERO
    ): List<Wallet>
}