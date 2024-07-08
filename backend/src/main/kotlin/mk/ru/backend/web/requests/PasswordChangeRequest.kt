package mk.ru.backend.web.requests

data class PasswordChangeRequest(
    val newPassword: String,
    val newPasswordConfirm: String
)