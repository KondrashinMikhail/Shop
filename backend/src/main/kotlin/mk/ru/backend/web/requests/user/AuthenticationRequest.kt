package mk.ru.backend.web.requests.user

data class AuthenticationRequest(
    val login: String,
    val password: String
)
