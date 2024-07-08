package mk.ru.backend.web.requests

data class AuthenticationRequest(
    val login: String,
    val password: String
)
