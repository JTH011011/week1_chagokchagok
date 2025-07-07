// 📄 com.jth.chagokchagok.data.remote.api.BudgetApi.kt
package com.jth.chagokchagok.data.remote.api

import com.jth.chagokchagok.data.remote.dto.BudgetRequest
import com.jth.chagokchagok.data.remote.dto.BudgetResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface BudgetApi {

    @PUT("/budget/user/{userId}/month/{yearMonth}")
    suspend fun updateBudget(
        @Path("userId") userId: String,
        @Path("yearMonth") yearMonth: String,  // 예: "2025-07"
        @Body request: BudgetRequest
    ): Response<BudgetResponse>
}

