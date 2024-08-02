package mk.ru.backend.schedulers

import mk.ru.backend.services.wallet.WalletService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class BalanceScheduler(private val walletService: WalletService) {
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    fun addBonuses() = walletService.addBonuses()
}