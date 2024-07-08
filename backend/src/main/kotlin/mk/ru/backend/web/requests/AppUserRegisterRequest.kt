package mk.ru.backend.web.requests

data class AppUserRegisterRequest(
    val login: String,
    val password: String,
    val mail: String
)
