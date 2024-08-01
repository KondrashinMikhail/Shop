package mk.ru.backend.persistence.repositories

import java.util.UUID
import mk.ru.backend.persistence.entities.OuterRemittance
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface OuterRemittanceRepo : JpaRepository<OuterRemittance, UUID>, JpaSpecificationExecutor<OuterRemittance>