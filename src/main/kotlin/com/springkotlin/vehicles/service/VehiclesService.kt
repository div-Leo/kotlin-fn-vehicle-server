package com.springkotlin.vehicles.service

import com.springkotlin.vehicles.dto.VehicleDTO
import com.springkotlin.vehicles.entity.FreeNow
import com.springkotlin.vehicles.entity.ShareNow
import com.springkotlin.vehicles.repositiry.FreeNowRepository
import com.springkotlin.vehicles.repositiry.ShareNowRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import java.util.stream.Stream


fun <T> concatPages(page1: Page<T>, page2: Page<T>): Page<T> {
    val combinedList = Stream.concat(page1.content.stream(), page2.content.stream())
        .collect(Collectors.toList())
    return PageImpl(combinedList, page1.pageable, page1.totalElements + page2.totalElements)
}

fun freeNowEntityToDTO (it: FreeNow) = VehicleDTO(
    id = it.id,
    type = "FREENOW",
    state = it.state,
    condition = it.condition,
    coordinates = listOf(it.latitude, it.longitude),
    engineType = it.engineType,
    licencePlate = it.licencePlate,
    address = null,
    fuel = null,
)

fun shareNowEntityToDTO (it: ShareNow) = VehicleDTO(
        id = it.id,
        type = "SHARENOW",
        condition = it.condition,
        address = it.address,
        coordinates = listOf(it.latitude, it.longitude),
        engineType = it.engineType,
        fuel = it.fuel,
        licencePlate = it.licencePlate,
        state = null
)

fun freeNowDTOToEntity (it: VehicleDTO) = FreeNow(
    id = it.id,
    state = it.state!!,
    condition = it.condition,
    latitude = it.coordinates[0],
    longitude = it.coordinates[1],
    engineType = it.engineType,
    licencePlate = it.licencePlate,
)

fun shareNowDTOToEntity (it: VehicleDTO) = ShareNow(
    id = it.id,
    condition = it.condition,
    address = it.address!!,
    latitude = it.coordinates[0],
    longitude = it.coordinates[1],
    engineType = it.engineType,
    fuel = it.fuel!!,
    licencePlate = it.licencePlate,
)

@Service
class VehiclesService (
    private val shareNowRepository: ShareNowRepository,
    private val freeNowRepository: FreeNowRepository
) {

    fun retrieveAllShareNowVehicles(type: String?, pageable: Pageable) : Page<VehicleDTO> {
        val newPageable = PageRequest.of(pageable.pageNumber, pageable.pageSize / 2, pageable.sort)

        val shareNowVehiclesDTO: Page<VehicleDTO> = shareNowRepository.findAll(newPageable).map {
            shareNowEntityToDTO(it)
        }

        val freeNowVehiclesDTO = freeNowRepository.findAll(newPageable).map {
            freeNowEntityToDTO(it)
        }


        return concatPages(shareNowVehiclesDTO, freeNowVehiclesDTO)
    }

    fun addVehicle(vehicleDTO: VehicleDTO): VehicleDTO {
        if (vehicleDTO.type === "FREENOW") {
            val vehicleEntity = freeNowDTOToEntity(vehicleDTO)
            freeNowRepository.save(vehicleEntity)

            return freeNowEntityToDTO(vehicleEntity)

        } else if (vehicleDTO.type === "SHARENOW") {
            val vehicleEntity = shareNowDTOToEntity(vehicleDTO)
            shareNowRepository.save(vehicleEntity)

            return shareNowEntityToDTO(vehicleEntity)
        } else {
//            throw VehicleMissingTypeException("type should be specified in Vehicle, please assign SHARENOW or FREENOW to property 'type'")
        }

        return vehicleDTO
    }

    fun updateVehicle(vehicleId: Int, vehicleDTO: VehicleDTO) {
        val freeNowVehicle = freeNowRepository.findById(vehicleId)

        if (freeNowVehicle.isPresent) {
            freeNowVehicle.get()
                .let {
                    it.state = vehicleDTO.state!!
                    it.condition = vehicleDTO.condition
                    it.latitude = vehicleDTO.coordinates[0]
                    it.longitude = vehicleDTO.coordinates[1]
                    it.engineType = vehicleDTO.engineType
                    it.licencePlate = vehicleDTO.licencePlate

                    freeNowRepository.save(it)
                    freeNowEntityToDTO(it)
                }
        } else {
            val shareNowVehicle = shareNowRepository.findById(vehicleId)

            if (shareNowVehicle.isPresent) {
                shareNowVehicle.get()
                    .let {
                        it.condition = vehicleDTO.condition
                        it.address = vehicleDTO.address!!
                        it.latitude = vehicleDTO.coordinates[0]
                        it.longitude = vehicleDTO.coordinates[1]
                        it.engineType = vehicleDTO.engineType
                        it.fuel = vehicleDTO.fuel!!
                        it.licencePlate = vehicleDTO.licencePlate

                        shareNowRepository.save(it)
                        shareNowEntityToDTO(it)
                    }
            } else {
//                throw CourseNotFoundException("No Vehicle found for the passed in Id : $vehicleID")
            }
        }
    }

    fun deleteVehicle(vehicleId: Int) {
        val freeNowVehicle = freeNowRepository.findById(vehicleId)

        if (freeNowVehicle.isPresent) {
            freeNowVehicle.get()
                .let {
                    freeNowRepository.deleteById(vehicleId)
                }
        } else {
            val shareNowVehicle = shareNowRepository.findById(vehicleId)

            if (shareNowVehicle.isPresent) {
                shareNowVehicle.get()
                    .let {
                        shareNowRepository.deleteById(vehicleId)
                    }
            } else {
//                throw CourseNotFoundException("No Vehicle found for the passed in id : $vehicleID")
            }
        }
    }
}