package com.jth.chagokchagok.data.remote.api

import com.jth.chagokchagok.data.remote.dto.LoginRequest
import com.jth.chagokchagok.data.remote.dto.LoginResponse
import com.jth.chagokchagok.data.remote.dto.SignupRequest
import com.jth.chagokchagok.data.remote.dto.SignupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("/user/signup")
    suspend fun signUp(@Body request: SignupRequest): Response<SignupResponse>

    @POST("/user/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}
