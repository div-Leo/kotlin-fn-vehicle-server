package com.springkotlin.vehicles.entity

import jakarta.persistence.*

@Entity
@Table(name = "ShareNow")
data class ShareNow (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    var condition: String,
    var address: String,
    var engineType: String,
    var latitude: Double,
    var longitude: Double,
    var fuel: Int,
    var licencePlate: String,
)