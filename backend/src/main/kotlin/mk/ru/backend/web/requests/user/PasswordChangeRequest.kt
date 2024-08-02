package mk.ru.backend.web.requests.user

data class PasswordChangeRequest(
    val newPassword: String,
    val newPasswordConfirm: String
)