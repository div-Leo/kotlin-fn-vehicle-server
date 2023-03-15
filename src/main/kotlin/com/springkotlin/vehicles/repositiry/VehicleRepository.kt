package com.springkotlin.vehicles.repositiry

import com.springkotlin.vehicles.entity.Vehicle
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface VehicleRepository: PagingAndSortingRepository<Vehicle, Int>, CrudRepository<Vehicle, Int> {
}