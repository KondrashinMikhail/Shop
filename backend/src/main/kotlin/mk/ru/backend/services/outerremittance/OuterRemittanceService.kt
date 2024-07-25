package mk.ru.backend.services.outerremittance

import java.math.BigDecimal
import mk.ru.backend.enums.OuterRemittanceType
import mk.ru.backend.persistence.entities.OuterRemittance
import mk.ru.backend.persistence.entities.Wallet

interface OuterRemittanceService {
    fun create(amount: BigDecimal, type: OuterRemittanceType, wallet: Wallet): OuterRemittance
}