package mk.ru.backend.mappers

import mk.ru.backend.enums.AppUserRole
import mk.ru.backend.persistence.entities.AppUser
import mk.ru.backend.web.requests.AppUserRegisterRequest
import mk.ru.backend.web.responses.pricehistory.PriceHistoryAppUserInfoResponse
import mk.ru.backend.web.responses.product.ProductAppUserInfoResponse
import mk.ru.backend.web.responses.user.AppUserInfoResponse
import mk.ru.backend.web.responses.user.AppUserRegisterResponse
import mk.ru.backend.web.responses.wallet.WalletAppUserInfoResponse
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

    fun toProductInfoResponse(appUser: AppUser): ProductAppUserInfoResponse = ProductAppUserInfoResponse(
        login = appUser.login!!,
        mail = appUser.mail!!,
        blocked = appUser.blocked
    )

    fun toWalletInfoResponse(appUser: AppUser): WalletAppUserInfoResponse = WalletAppUserInfoResponse(
        login = appUser.login!!,
        mail = appUser.mail!!,
    )

    fun toPriceHistoryInfoResponse(appUser: AppUser): PriceHistoryAppUserInfoResponse = PriceHistoryAppUserInfoResponse(
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