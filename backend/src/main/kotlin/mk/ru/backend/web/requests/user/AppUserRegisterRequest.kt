package mk.ru.backend.web.requests.user

data class AppUserRegisterRequest(
    val login: String,
    val password: String,
    val mail: String
)
