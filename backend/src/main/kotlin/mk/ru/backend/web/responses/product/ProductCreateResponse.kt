package mk.ru.backend.web.responses.product

import java.time.LocalDate
import java.util.UUID

data class ProductCreateResponse(
    val id: UUID,
    val registrationDate: LocalDate
)
