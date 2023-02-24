package com.springkotlin.vehicles.dto

data class VehicleDTO (
    val id: Int?,
    val type: String, // 'FREENOW' | 'SHARENOW'
    val state: String?, // 'BAD' | 'GOOD'
    val condition: String,
    val address: String?,
    val coordinates: List<Double>, // [latitude, longitude]
    val engineType: String,
    val fuel: Int?,
    val licencePlate: String,
)