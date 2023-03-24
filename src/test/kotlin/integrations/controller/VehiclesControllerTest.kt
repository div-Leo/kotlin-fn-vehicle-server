package integrations.controller

import com.springkotlin.vehicles.dto.VehicleDTO
import com.springkotlin.vehicles.dto.VehicleType
import com.springkotlin.vehicles.repositiry.FreeNowRepository
import com.springkotlin.vehicles.repositiry.ShareNowRepository
import integrations.utils.freeNowEntityList
import integrations.utils.shareNowEntityList
import jakarta.validation.Valid
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.bind.annotation.*

val freeNowVehicleDTO = VehicleDTO(
    id = null,
    type = VehicleType.FREENOW,
    state = "ACTIVE",
    condition = "GOOD",
    engineType = "PETROL",
    coordinates = listOf(67.4567890, 42.456789),
    licencePlate = "LD 95 0531",
)

val shareNowVehicleDTO = VehicleDTO(
    id = null,
    type = VehicleType.SHARENOW,
    condition = "BAD",
    address = "Via Solari 11",
    coordinates = listOf(67.4567890, 42.456789),
    engineType = "ELECTRIC",
    fuel = 99,
    licencePlate = "DV 95 0531",
)

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class VehiclesControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var freeNowRepository: FreeNowRepository

    @Autowired
    lateinit var shareNowRepository: ShareNowRepository

    @Test
    fun retrieveAllVehicles() {

    }

    @Test
    fun addVehicle() {

    }

    @Test
    fun updateVehicle() {

    }

    @Test
    fun deleteVehicle() {

    }
}