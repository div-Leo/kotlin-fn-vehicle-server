package com.springkotlin.vehicles.seeders

import com.springkotlin.vehicles.entity.Vehicle
import com.springkotlin.vehicles.repositiry.FreeNowRepository
import com.springkotlin.vehicles.repositiry.ShareNowRepository
import com.springkotlin.vehicles.repositiry.VehicleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class VehicleSeeder @Autowired constructor(
    private val shareNowRepository: ShareNowRepository,
    private val freeNowRepository: FreeNowRepository,
    private val vehicleRepository: VehicleRepository
) : CommandLineRunner {
    override fun run(vararg args: String?) {

        val shareNowList = shareNowRepository.findAll().map{
            Vehicle(licencePlate = it.licencePlate, shareNowVehicle = it)
        }
        val freeNowList = freeNowRepository.findAll().map{
            Vehicle(licencePlate = it.licencePlate, freeNowVehicle = it)
        }

        vehicleRepository.saveAll(shareNowList)
        vehicleRepository.saveAll(freeNowList)
    }
}
