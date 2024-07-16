package mk.ru.backend.services.token

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.util.Date
import mk.ru.backend.configurations.JwtProperties
import mk.ru.backend.enums.TokenType
import mk.ru.backend.exceptions.TokenException
import mk.ru.backend.services.user.AppUserDetailsService
import mk.ru.backend.web.responses.user.RefreshTokenResponse
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class TokenServiceImpl(
    private val jwtProperties: JwtProperties,
    private val appUserDetailsService: AppUserDetailsService
) : TokenService {
    private val secretKey = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())
    private val tokenTypeField: String = "tokenType"

    override fun generateToken(
        userDetails: UserDetails,
        expirationDate: Date,
        additionalClaims: Map<String, Any>
    ): String = jwtProperties.prefix +
            Jwts.builder()
                .claims()
                .subject(userDetails.username)
                .issuedAt(Date(System.currentTimeMillis()))
                .expiration(expirationDate)
                .add(additionalClaims)
                .and()
                .signWith(secretKey)
                .compact()

    override fun isTokenValid(token: String, userDetails: UserDetails): Boolean = userDetails.username ==
            getLoginFromToken(token) && getClaims(token).expiration.after(Date(System.currentTimeMillis()))

    override fun getLoginFromToken(token: String): String = getClaims(token).subject

    override fun refreshAccessToken(refreshToken: String): RefreshTokenResponse? {
        val subRefreshToken: String =
            if (refreshToken.startsWith(jwtProperties.prefix)) refreshToken.substringAfter(jwtProperties.prefix)
            else refreshToken

        if (getClaims(subRefreshToken)[tokenTypeField] != TokenType.REFRESH.name)
            throw TokenException("Invalid token type")

        val login: String = getLoginFromToken(subRefreshToken)
        return login.let {
            val currentUserDetails: UserDetails = appUserDetailsService.loadUserByUsername(login)
            if (isTokenValid(subRefreshToken, currentUserDetails))
                RefreshTokenResponse(
                    accessToken = createAccessToken(currentUserDetails),
                    refreshToken = createRefreshToken(currentUserDetails)
                )
            else null
        } ?: throw TokenException("Invalid refresh token")
    }

    override fun createAccessToken(user: UserDetails) = generateToken(
        userDetails = user,
        expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration),
        additionalClaims = mapOf(tokenTypeField to TokenType.ACCESS)
    )

    override fun createRefreshToken(user: UserDetails) = generateToken(
        userDetails = user,
        expirationDate = Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration),
        additionalClaims = mapOf(tokenTypeField to TokenType.REFRESH)
    )

    private fun getClaims(token: String): Claims = Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token)
        .payload
}