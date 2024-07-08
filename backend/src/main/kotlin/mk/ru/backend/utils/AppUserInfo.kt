package mk.ru.backend.utils

import mk.ru.backend.enums.AppUserRole
import mk.ru.backend.exceptions.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder

object AppUserInfo {
    fun checkAccessAllowed(requiredLogin: String? = "") {
        val securityContext = SecurityContextHolder.getContext().authentication
        if (securityContext.authorities.none { it.authority == AppUserRole.ADMIN.role })
            if (securityContext.name != requiredLogin)
                throw AccessDeniedException("Access for user with login - ${securityContext.name} is denied")
    }

    fun getAuthenticatedLogin(): String {
        return SecurityContextHolder.getContext().authentication.name
    }
}
