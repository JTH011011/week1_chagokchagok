// 📄 com.jth.chagokchagok.data.repository.BudgetRepository.kt
package com.jth.chagokchagok.data.repository

import com.jth.chagokchagok.data.remote.RetrofitProvider
import com.jth.chagokchagok.data.remote.dto.BudgetRequest
import com.jth.chagokchagok.data.remote.dto.BudgetResponse
import retrofit2.HttpException

class BudgetRepository {

    private val api = RetrofitProvider.budgetApi

    suspend fun updateBudget(userId: String, yearMonth: String, request: BudgetRequest): Result<BudgetResponse> {
        return try {
            val response = api.updateBudget(userId, yearMonth, request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                val error = response.errorBody()?.string()
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
