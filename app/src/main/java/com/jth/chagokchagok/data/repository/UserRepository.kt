package com.jth.chagokchagok.data.repository

import com.jth.chagokchagok.data.remote.RetrofitProvider
import com.jth.chagokchagok.data.remote.dto.LoginRequest
import com.jth.chagokchagok.data.remote.dto.LoginResponse
import com.jth.chagokchagok.data.remote.dto.SignupRequest
import com.jth.chagokchagok.data.remote.dto.SignupResponse
import retrofit2.HttpException

class UserRepository {
    private val api = RetrofitProvider.userApi

    suspend fun signUp(request: SignupRequest): Result<SignupResponse> = runCatching {
        val response = api.signUp(request)

        if (response.isSuccessful) {
            response.body() ?: throw Exception("응답이 비어 있습니다.")
        } else {
            val error = response.errorBody()?.string() ?: "회원가입 실패"
            throw HttpException(response)
        }
    }

    suspend fun login(request: LoginRequest): Result<LoginResponse> {
        return try {
            val response = api.login(request)
            val body = response.body()

            // ✅ 성공 조건: userId, username 등이 null이 아님
            if (response.isSuccessful && body != null && body.userId != null) {
                Result.success(body)
            } else {
                Result.failure(Exception("login 실패"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
