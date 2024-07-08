package mk.ru.backend.services.user

import mk.ru.backend.persistence.entities.AppUser
import mk.ru.backend.web.requests.AppUserRegisterRequest
import mk.ru.backend.web.requests.PasswordChangeRequest
import mk.ru.backend.web.responses.user.AppUserRegisterResponse

interface AppUserService {
    fun register(registerRequest: AppUserRegisterRequest): AppUserRegisterResponse
    fun changePassword(login: String, passwordChangeRequest: PasswordChangeRequest)
    fun block(login: String)
    fun restore(login: String)
    fun findEntityByLogin(login: String, blockedCheck: Boolean = false): AppUser
}