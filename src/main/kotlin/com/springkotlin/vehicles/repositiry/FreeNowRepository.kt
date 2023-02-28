package com.springkotlin.vehicles.repositiry

import com.springkotlin.vehicles.entity.FreeNow
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface FreeNowRepository: PagingAndSortingRepository<FreeNow, Int>, CrudRepository<FreeNow, Int> {
}