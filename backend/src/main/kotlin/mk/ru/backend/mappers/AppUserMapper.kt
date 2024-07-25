package mk.ru.backend.mappers

import mk.ru.backend.enums.AppUserRole
import mk.ru.backend.persistence.entities.AppUser
import mk.ru.backend.web.requests.AppUserRegisterRequest
import mk.ru.backend.web.responses.pricehistory.PriceHistoryAppUserResponse
import mk.ru.backend.web.responses.product.ProductAppUserResponse
import mk.ru.backend.web.responses.user.AppUserInfoResponse
import mk.ru.backend.web.responses.user.AppUserRegisterResponse
import mk.ru.backend.web.responses.wallet.WalletAppUserResponse
import org.springframework.stereotype.Component

@Component
class AppUserMapper {
    fun toEntity(user: AppUserRegisterRequest): AppUser = AppUser(
        login = user.login,
        password = user.password,
        mail = user.mail,
        role = AppUserRole.USER,
    )

    fun toRegisterResponse(appUser: AppUser): AppUserRegisterResponse = AppUserRegisterResponse(
        login = appUser.login!!,
        registrationDate = appUser.registrationDate!!
    )

    fun toProductResponse(appUser: AppUser): ProductAppUserResponse = ProductAppUserResponse(
        login = appUser.login!!,
        mail = appUser.mail!!,
        blocked = appUser.blocked
    )

    fun toWalletResponse(appUser: AppUser): WalletAppUserResponse = WalletAppUserResponse(
        login = appUser.login!!,
        mail = appUser.mail!!,
    )

    fun toPriceHistoryResponse(appUser: AppUser): PriceHistoryAppUserResponse = PriceHistoryAppUserResponse(
        login = appUser.login!!,
        mail = appUser.mail!!,
    )

    fun toInfoResponse(appUser: AppUser): AppUserInfoResponse = AppUserInfoResponse(
        login = appUser.login!!,
        mail = appUser.mail!!,
        blocked = appUser.blocked,
        registrationDate = appUser.registrationDate!!
    )
}