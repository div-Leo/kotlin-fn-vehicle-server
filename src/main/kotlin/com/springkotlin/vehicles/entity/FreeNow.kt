package com.springkotlin.vehicles.entity

import jakarta.persistence.*

@Entity
@Table(name = "FreeNow")
data class FreeNow (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,
    var state: String,
    var condition: String,
    var engineType: String,
    var latitude: Double,
    var longitude: Double,
    var licencePlate: String,

    @OneToOne(mappedBy = "freeNowVehicle", cascade = [CascadeType.ALL])
    var vehicle: Vehicle? = null
)