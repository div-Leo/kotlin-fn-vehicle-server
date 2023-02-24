package com.springkotlin.vehicles.seeders

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.springkotlin.vehicles.entity.ShareNow
import com.springkotlin.vehicles.repositiry.ShareNowRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
@Component
class ShareNowSeeder @Autowired constructor(
        private val shareNowRepository: ShareNowRepository // replace with your own repository class
    ) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val mapper = jacksonObjectMapper()
        val fixtures: List<ShareNow> = mapper.readValue(javaClass.classLoader.getResource("fixtures/vehicles-share-now.json")!!.readText())

        shareNowRepository.saveAll(fixtures)
    }
}