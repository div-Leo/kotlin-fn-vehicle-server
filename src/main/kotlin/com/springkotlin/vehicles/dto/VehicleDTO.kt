package com.springkotlin.vehicles.dto
data class VehicleDTO (
    val id: Long?,
    val type: VehicleType,
    val state: String? = null, // 'BAD' | 'GOOD'
    val condition: String,
    val address: String? = null,
    val coordinates: List<Double>, // [latitude, longitude]
    val engineType: String,
    val fuel: Int? = null,
    val licencePlate: String
)

enum class VehicleType {
    FREENOW,
    SHARENOW
}
