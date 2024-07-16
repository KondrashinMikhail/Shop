package mk.ru.backend.persistence.repositories

import java.util.UUID
import mk.ru.backend.persistence.entities.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface ProductRepo : JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product>
