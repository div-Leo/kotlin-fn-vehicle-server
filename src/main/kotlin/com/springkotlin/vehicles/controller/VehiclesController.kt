package com.springkotlin.vehicles.controller

import com.springkotlin.vehicles.dto.VehicleDTO
import com.springkotlin.vehicles.dto.VehicleType
import com.springkotlin.vehicles.service.VehiclesService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/vehicles")
class VehiclesController (val vehiclesService : VehiclesService) {
    @GetMapping
    fun retrieveAllVehicles(
        @RequestParam("type", required = false) type: VehicleType?,
        pageable: Pageable
    ) : Page<VehicleDTO>
    = vehiclesService.retrieveAllShareNowVehicles(type, pageable)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addVehicle(@RequestBody @Valid vehicleDTO: VehicleDTO ): VehicleDTO
        = vehiclesService.addVehicle(vehicleDTO)

    @PutMapping("/{vehicle_id}")
    fun updateVehicle(@RequestBody vehicleDTO: VehicleDTO, @PathVariable("vehicle_id") vehicleId: Int )
    = vehiclesService.updateVehicle(vehicleId, vehicleDTO)

    @DeleteMapping("/{vehicle_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteVehicle(@PathVariable("vehicle_id") vehicleId: Int)
    = vehiclesService.deleteVehicle(vehicleId)
}