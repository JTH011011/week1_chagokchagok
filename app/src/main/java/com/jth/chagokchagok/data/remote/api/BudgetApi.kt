// 📄 com.jth.chagokchagok.data.remote.api.BudgetApi.kt
package com.jth.chagokchagok.data.remote.api

import com.jth.chagokchagok.data.remote.dto.BudgetRequest
import com.jth.chagokchagok.data.remote.dto.BudgetResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BudgetApi {

    @POST("/budget/user/{userId}/month/{yearMonth}")
    suspend fun createBudget(
        @Path("userId") userId: String,
        @Path("yearMonth") yearMonth: String,  // 예: "2025-07"
        @Body request: BudgetRequest
    ): Response<BudgetResponse>

    @GET("/budget/user/{userId}/month/{yearMonth}")
    suspend fun getBudget(
        @Path("userId") userId: String,
        @Path("yearMonth") yearMonth: String  // 예: "2025-07"
    ): Response<BudgetResponse>
}

