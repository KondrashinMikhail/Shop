package mk.ru.backend.web.responses.user

import java.time.LocalDate

data class AppUserRegisterResponse(
    val login: String,
    val registrationDate: LocalDate,
)
