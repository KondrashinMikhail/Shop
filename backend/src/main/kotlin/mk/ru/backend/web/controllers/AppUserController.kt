package mk.ru.backend.web.controllers

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import mk.ru.backend.services.authentication.AuthenticationService
import mk.ru.backend.services.token.TokenService
import mk.ru.backend.services.user.AppUserService
import mk.ru.backend.services.wallet.WalletService
import mk.ru.backend.utils.SwaggerUtils
import mk.ru.backend.web.requests.user.AppUserRegisterRequest
import mk.ru.backend.web.requests.user.AuthenticationRequest
import mk.ru.backend.web.requests.user.PasswordChangeRequest
import mk.ru.backend.web.responses.user.AppUserInfoResponse
import mk.ru.backend.web.responses.user.AppUserRegisterResponse
import mk.ru.backend.web.responses.user.AuthenticationResponse
import mk.ru.backend.web.responses.user.RefreshTokenResponse
import mk.ru.backend.web.responses.wallet.WalletInfoResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
@SecurityRequirement(name = SwaggerUtils.SECURITY_SCHEME_NAME)
class AppUserController(
    private val appUserService: AppUserService,
    private val authenticationService: AuthenticationService,
    private val tokenService: TokenService,
    private val walletService: WalletService,
) {
    @PostMapping("/login")
    fun authenticate(@RequestBody authRequest: AuthenticationRequest): ResponseEntity<AuthenticationResponse> =
        ResponseEntity.ok(authenticationService.authenticate(authRequest))

    @PostMapping("/register")
    fun register(@RequestBody appUserRegisterRequest: AppUserRegisterRequest): ResponseEntity<AppUserRegisterResponse> =
        ResponseEntity.ok(appUserService.register(appUserRegisterRequest))

    @PostMapping("/refresh")
    fun refresh(@RequestBody refreshToken: String): ResponseEntity<RefreshTokenResponse> =
        ResponseEntity.ok(tokenService.refreshAccessToken(refreshToken))

    @PatchMapping("/{login}/change-password")
    fun changePassword(
        @PathVariable login: String,
        @RequestBody passwordChangeRequest: PasswordChangeRequest
    ) = appUserService.changePassword(login = login, passwordChangeRequest = passwordChangeRequest)

    @DeleteMapping("/{login}/block")
    fun block(@PathVariable login: String) = appUserService.block(login)

    @PatchMapping("/{login}/restore")
    fun restore(@PathVariable login: String) = appUserService.restore(login)

    @GetMapping("/{login}/info")
    fun findInfo(@PathVariable login: String): ResponseEntity<AppUserInfoResponse> =
        ResponseEntity.ok(appUserService.findInfo(login))

    @GetMapping("/wallet")
    fun findAuthenticatedWallet(): ResponseEntity<WalletInfoResponse> =
        ResponseEntity.ok(walletService.findByAuthenticatedLogin())
}