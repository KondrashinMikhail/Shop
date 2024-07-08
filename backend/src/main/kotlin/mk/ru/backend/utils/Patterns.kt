package mk.ru.backend.utils

object Patterns {
    const val MAIL_PATTERN: String =
        "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"

    /**
     * По крайней мере одну строчную букву,
     * По крайней мере одну заглавную букву,
     * По крайней мере одну цифру,
     * По крайней мере один из специальных символов (@, $, !, %, *, ?, &),
     * И будет состоять из не менее 8 символов.
     * */
    const val PASSWORD_PATTERN: String = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"

    /**
     * По крайней мере одну строчную букву,
     * По крайней мере одну заглавную букву,
     * */
    const val LOGIN_PATTERN: String = "^(?=.*[A-Z])(?=.*[a-z]).+\$"

    val mailRegex = Regex(MAIL_PATTERN)
    val passwordRegex = Regex(PASSWORD_PATTERN)
    val loginRegex = Regex(LOGIN_PATTERN)
}