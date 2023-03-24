package com.springkotlin.vehicles.entity

import jakarta.persistence.*

@Entity
@Table(name = "ShareNow")
data class ShareNow (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,
    var condition: String,
    var address: String,
    var engineType: String,
    var latitude: Double,
    var longitude: Double,
    var fuel: Int,
    var licencePlate: String,

    // on the referenced table we need to say what's the relation and say by which key is mapped
    @OneToOne(mappedBy = "shareNowVehicle", cascade = [CascadeType.ALL])
    val vehicle: Vehicle? = null
)