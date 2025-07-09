package com.jth.chagokchagok.data.remote

import com.jth.chagokchagok.data.remote.api.BudgetApi
import com.jth.chagokchagok.data.remote.api.UserApi
import com.jth.chagokchagok.data.remote.api.PerformanceApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitProvider {

    /** 에뮬레이터에서 PC 로컬서버에 접속하려면 ‘localhost’ 대신 10.0.2.2 사용 */
    private const val BASE_URL = "http://143.248.216.95:8080/"

    /** ── ① OkHttp 로깅 인터셉터 ───────────────────────────── */
    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.HEADERS   //   BODY = URL · Header · JSON 모두 표시
    }

    private val client: OkHttpClient = OkHttpClient
        .Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(logger)                    // ② 여기에 붙이기
        .build()

    /** ── ③ Retrofit 한 번만 만들고 재사용 ───────────────── */
    private val retrofit: Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .client(client)                            // ④ 로깅 클라이언트 주입
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /** ── ④ API 인스턴스 ─────────────────────────────────── */
    val userApi: UserApi by lazy { retrofit.create(UserApi::class.java) }
    val budgetApi: BudgetApi by lazy { retrofit.create(BudgetApi::class.java) }
    val performanceApi: PerformanceApi by lazy { retrofit.create(PerformanceApi::class.java) }
}

