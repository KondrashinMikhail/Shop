package mk.ru.backend.web.responses.user

import java.time.LocalDate

data class AppUserInfoResponse(
    val login: String,
    val mail: String,
    val blocked: Boolean,
    val registrationDate: LocalDate
)
