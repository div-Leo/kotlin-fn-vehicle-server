package integrations.repository

import com.springkotlin.vehicles.repositiry.FreeNowRepository
import com.springkotlin.vehicles.repositiry.ShareNowRepository
import integrations.utils.freeNowEntityList
import integrations.utils.shareNowEntityList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class FreeNowRepositoryTest {

    @Autowired
    lateinit var freeNowRepository: FreeNowRepository

    @Autowired
    lateinit var shareNowRepository: ShareNowRepository

    @BeforeEach
    fun setUp () {
        freeNowRepository.deleteAll()
        shareNowRepository.deleteAll()

        val freeNowVehiclesList = freeNowEntityList()
        freeNowRepository.saveAll(freeNowVehiclesList)

        val shareNowVehiclesList = shareNowEntityList()
        shareNowRepository.saveAll(shareNowVehiclesList)

    }

    @Test
    fun getFirstPageByDefault(){
        val FNVehicles = freeNowRepository.findAll()
        println("vehicles : $FNVehicles")

        Assertions.assertEquals(3, FNVehicles.size)

        val SNVehicles = shareNowRepository.findAll()
        println("vehicles : $SNVehicles")

        Assertions.assertEquals(3, SNVehicles.size)

    }


}