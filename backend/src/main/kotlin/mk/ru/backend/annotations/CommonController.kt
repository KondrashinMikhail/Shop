package mk.ru.backend.annotations

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import mk.ru.backend.utils.SwaggerUtils
import org.springframework.web.bind.annotation.RestController

@RestController
@SecurityRequirement(name = SwaggerUtils.SECURITY_SCHEME_NAME)
annotation class CommonController
