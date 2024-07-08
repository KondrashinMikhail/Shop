package mk.ru.backend.services.transaction

import java.math.BigDecimal
import java.util.UUID
import mk.ru.backend.persistence.entities.Product
import mk.ru.backend.persistence.entities.Transaction
import mk.ru.backend.persistence.entities.Wallet

interface TransactionService {
    fun create(
        senderWallet: Wallet,
        recipientWallet: Wallet,
        amount: BigDecimal,
        feeAmount: BigDecimal,
        feePercent: BigDecimal,
        product: Product
    ): Transaction

    fun findEntityById(id: UUID): Transaction
}