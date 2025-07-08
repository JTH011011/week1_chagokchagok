package com.jth.chagokchagok.data.remote.api

import com.jth.chagokchagok.data.remote.dto.PerformanceResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PerformanceApi {
    @GET("performance/user/{userId}/month/{yearMonth}")
    suspend fun getPerformancesByUserAndMonth(
        @Path("userId") userId: String,
        @Path("yearMonth") yearMonth: String
    ): List<PerformanceResponseDTO>

    @GET("performance/photourls")
    suspend fun getPhotoUrlsByYearMonth(
        @Query("year") year: Int,
        @Query("month") month: Int
    ): List<String>
}
