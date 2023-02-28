package com.springkotlin.vehicles.entity

import jakarta.persistence.*

@Entity
@Table(name = "FreeNow")
data class FreeNow (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    var state: String,
    var condition: String,
    var engineType: String,
    var latitude: Double,
    var longitude: Double,
    var licencePlate: String,
)