package com.jth.chagokchagok.data.remote.api

import com.jth.chagokchagok.data.remote.dto.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface PerformanceApi {

    /** 이미지 파일 업로드 → URL 반환 */
    @Multipart
    @POST("file/upload")          // ← 백엔드 실제 엔드포인트에 맞춰 수정
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): Response<ImageUrlResponse> // {"url": ".../photo.jpg"}

    /** 공연 정보 등록 (URL 전송) */
    @POST("performance")
    suspend fun createPerformance(
        @Body request: PerformanceRequest
    ): Response<PerformanceResponseDto>

    /** 유저 공연 목록 조회 */
    @GET("performance/user/{userId}")
    suspend fun getUserPerformances(
        @Path("userId") userId: String
    ): Response<List<PerformanceResponseDto>>

    @GET("/performance/photourls")
    suspend fun getPhotoUrlsByYearMonth(
        @Query("userId") userId: String, // 유저 ID
        @Query("year") year: Int,
        @Query("month") month: Int
    ): List<String>  // ← 실제 응답 타입에 따라 바꿔야 함. 예: List<PerformanceResponseDto>

    @GET("performance/user/{userId}/month/{yearMonth}/photourls")
    suspend fun getPerformancesWithPhotoUrls(
        @Path("userId") userId: String,
        @Path("yearMonth") yearMonth: String
    ): Response<List<PerformanceResponseDto>>

}
