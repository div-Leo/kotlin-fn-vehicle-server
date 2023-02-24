package com.springkotlin.vehicles.seeders

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.springkotlin.vehicles.entity.FreeNow
import com.springkotlin.vehicles.repositiry.FreeNowRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
@Component
class FreeNowSeeder @Autowired constructor(
        private val freeNowRepository: FreeNowRepository // replace with your own repository class
    ) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val mapper = jacksonObjectMapper()
        val fixtures: List<FreeNow> = mapper.readValue(javaClass.classLoader.getResource("fixtures/vehicles-free-now.json")!!.readText())

        freeNowRepository.saveAll(fixtures)
    }
}