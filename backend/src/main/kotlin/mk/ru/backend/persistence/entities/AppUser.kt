package mk.ru.backend.persistence.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.validation.constraints.Pattern
import java.time.LocalDate
import mk.ru.backend.enums.AppUserRole
import mk.ru.backend.utils.Patterns
import org.hibernate.annotations.CreationTimestamp

@Entity
data class AppUser(
    @Id
    @Pattern(regexp = Patterns.LOGIN_PATTERN)
    var login: String? = null,
    @Column(nullable = false)
    @Pattern(regexp = Patterns.PASSWORD_PATTERN)
    var password: String? = null,
    @Column(nullable = false, unique = true)
    @Pattern(regexp = Patterns.MAIL_PATTERN)
    var mail: String? = null,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var role: AppUserRole = AppUserRole.USER,
    @Column(nullable = false)
    var blocked: Boolean = false,
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    var registrationDate: LocalDate? = LocalDate.now(),
    @OneToMany(targetEntity = Product::class, mappedBy = "owner", fetch = FetchType.LAZY)
    var products: List<Product>? = null,
    @OneToOne(targetEntity = Wallet::class, fetch = FetchType.LAZY)
    var wallet: Wallet? = null
)
