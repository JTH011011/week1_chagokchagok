package com.jth.chagokchagok.data.remote

import com.jth.chagokchagok.data.remote.api.UserApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.jth.chagokchagok.data.remote.api.BudgetApi

object RetrofitProvider {
    private const val BASE_URL = "http://localhost:8080" // 에뮬레이터 → 로컬 서버

    val userApi: UserApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }

    val budgetApi: BudgetApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BudgetApi::class.java)
    }
}
