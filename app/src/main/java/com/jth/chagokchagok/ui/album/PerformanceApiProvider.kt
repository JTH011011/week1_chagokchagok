package com.jth.chagokchagok.ui.album

import com.jth.chagokchagok.data.remote.api.PerformanceApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PerformanceApiProvider {

    val api: PerformanceApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://143.248.181.90:8080/") // ← 실제 서버 주소로 바꾸기
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PerformanceApi::class.java)
    }
}