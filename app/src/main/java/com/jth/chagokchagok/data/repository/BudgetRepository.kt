package com.jth.chagokchagok.data.repository

import com.jth.chagokchagok.data.remote.RetrofitProvider
import com.jth.chagokchagok.data.remote.dto.BudgetRequest
import com.jth.chagokchagok.data.remote.dto.BudgetResponse
import retrofit2.HttpException

class BudgetRepository {

    private val api = RetrofitProvider.budgetApi

    // 📌 예산 생성
    suspend fun createBudget(
        userId: String,
        yearMonth: String,
        request: BudgetRequest
    ): Result<BudgetResponse> {
        return try {
            val response = api.createBudget(userId, yearMonth, request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 📌 예산 조회
    suspend fun getBudget(
        userId: String,
        yearMonth: String
    ): Result<BudgetResponse> {
        return try {
            val response = api.getBudget(userId, yearMonth)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

