package com.springkotlin.vehicles.entity

import jakarta.persistence.*

@Entity
@Table(name = "Vehicle")
data class Vehicle (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column(name = "licencePlate", unique = true)
    var licencePlate: String,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="freeNowVehicle", referencedColumnName = "id")
    var freeNowVehicle: FreeNow? = null,
    // the table with the key needs a join column and refer to a column from the other table
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="shareNowVehicle",referencedColumnName = "id")
    var shareNowVehicle: ShareNow? = null
)