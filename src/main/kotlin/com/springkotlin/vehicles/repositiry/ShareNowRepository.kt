package com.springkotlin.vehicles.repositiry

import com.springkotlin.vehicles.entity.ShareNow
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface ShareNowRepository: PagingAndSortingRepository<ShareNow, Int>, CrudRepository<ShareNow, Int> {
}