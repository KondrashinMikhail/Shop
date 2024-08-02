package mk.ru.backend.services.authentication

import mk.ru.backend.web.requests.user.AuthenticationRequest
import mk.ru.backend.web.responses.user.AuthenticationResponse

interface AuthenticationService {
    fun authenticate(authenticationRequest: AuthenticationRequest): AuthenticationResponse
}