package mk.ru.backend.mappers

import mk.ru.backend.persistence.entities.OuterRemittance
import mk.ru.backend.web.responses.wallet.WalletOuterRemittanceResponse
import org.springframework.stereotype.Component

@Component
class OuterRemittanceMapper {
    fun toWalletResponse(outerRemittance: OuterRemittance): WalletOuterRemittanceResponse =
        WalletOuterRemittanceResponse(
            id = outerRemittance.id!!,
            date = outerRemittance.date,
            amount = outerRemittance.amount,
        )
}