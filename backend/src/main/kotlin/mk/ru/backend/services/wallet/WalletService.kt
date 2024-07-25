package mk.ru.backend.services.wallet

import java.util.UUID
import mk.ru.backend.persistence.entities.AppUser
import mk.ru.backend.persistence.entities.Transaction
import mk.ru.backend.persistence.entities.Wallet
import mk.ru.backend.web.responses.wallet.WalletInfoResponse

interface WalletService {
    fun save(wallet: Wallet): Wallet
    fun create(owner: AppUser): Wallet
    fun getFromBalance(transaction: Transaction, wallet: Wallet)
    fun addToBalance(transaction: Transaction, wallet: Wallet)
    fun findEntityById(id: UUID): Wallet
    fun findByAuthenticatedLogin(): WalletInfoResponse
}