package mk.ru.backend.configurations

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import mk.ru.backend.utils.SwaggerUtils
import org.springframework.context.annotation.Configuration

@Configuration
@SecurityScheme(
    name = SwaggerUtils.SECURITY_SCHEME_NAME,
    scheme = SwaggerUtils.SECURITY_SCHEME,
    bearerFormat = SwaggerUtils.BEARER_FORMAT,
    type = SecuritySchemeType.HTTP,
    `in` = SecuritySchemeIn.HEADER
)
class SwaggerConfig