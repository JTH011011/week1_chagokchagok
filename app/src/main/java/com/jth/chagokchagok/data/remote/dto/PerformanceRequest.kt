package com.jth.chagokchagok.data.remote.dto

data class PerformanceRequest(
    val userId: String,
    val name: String,
    val genre: String,
    val cast: String,
    val attendingDate: String, // ISO-8601 형식: "2025-07-08T00:00:00"
    val seat: String,
    val price: Int,
    val photoUrl: String// base64 인코딩된 문자열 (nullable)
)

data class ImageUrlResponse(
    val url: String
)