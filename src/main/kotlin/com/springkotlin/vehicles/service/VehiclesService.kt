package com.springkotlin.vehicles.service

import com.springkotlin.vehicles.dto.VehicleDTO
import com.springkotlin.vehicles.dto.VehicleType
import com.springkotlin.vehicles.entity.FreeNow
import com.springkotlin.vehicles.entity.ShareNow
import com.springkotlin.vehicles.entity.Vehicle
import com.springkotlin.vehicles.exeptions.VehicleMissingTypeException
import com.springkotlin.vehicles.exeptions.VehicleNotFoundException
import com.springkotlin.vehicles.repositiry.FreeNowRepository
import com.springkotlin.vehicles.repositiry.ShareNowRepository
import com.springkotlin.vehicles.repositiry.VehicleRepository
import org.springframework.data.domain.*
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
    type = VehicleType.FREENOW,
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
        type = VehicleType.SHARENOW,
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
    private val freeNowRepository: FreeNowRepository,
    private val vehicleRepository: VehicleRepository,
) {

    fun retrieveAllShareNowVehicles(type: VehicleType?, pageable: Pageable) : Page<VehicleDTO> {
        if (type === VehicleType.FREENOW) {
            val freeNowVehiclesDTO = freeNowRepository.findAll(pageable).map {
                freeNowEntityToDTO(it)
            }

            return freeNowVehiclesDTO

        } else if (type === VehicleType.SHARENOW) {
            val shareNowVehiclesDTO: Page<VehicleDTO> = shareNowRepository.findAll(pageable).map {
                shareNowEntityToDTO(it)
            }
            return shareNowVehiclesDTO
        } else {
            val newPageable = PageRequest.of(pageable.pageNumber, pageable.pageSize, Sort.by("licencePlate"))
            return vehicleRepository.findAll(newPageable).map {
                it.freeNowVehicle?.let { fn ->
                    freeNowEntityToDTO(fn)
                } ?: run {
                    it.shareNowVehicle?.let { sn ->
                        shareNowEntityToDTO(sn)
                    }
                }
            }
        }
    }

    fun addVehicle(vehicleDTO: VehicleDTO): VehicleDTO {
        return if (vehicleDTO.type === VehicleType.FREENOW) {
            val vehicleEntity = freeNowDTOToEntity(vehicleDTO)
            val persistedEntity = freeNowRepository.save(vehicleEntity)

            val vehicle = Vehicle(licencePlate = persistedEntity.licencePlate, freeNowVehicle = persistedEntity)
            vehicleRepository.save(vehicle)

            freeNowEntityToDTO(persistedEntity)
        } else if (vehicleDTO.type === VehicleType.SHARENOW) {
            val vehicleEntity = shareNowDTOToEntity(vehicleDTO)
            val persistedEntity = shareNowRepository.save(vehicleEntity)

            val vehicle = Vehicle(licencePlate = persistedEntity.licencePlate, shareNowVehicle = persistedEntity)
            vehicleRepository.save(vehicle)

            shareNowEntityToDTO(persistedEntity)
        } else {
            throw VehicleMissingTypeException("type should be specified in Vehicle, please assign SHARENOW or FREENOW to property 'type'")
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
                throw VehicleNotFoundException("No Vehicle found for the passed in Id : $vehicleId")
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
                throw VehicleNotFoundException("No Vehicle found for the passed in Id : $vehicleId")
            }
        }
    }
}