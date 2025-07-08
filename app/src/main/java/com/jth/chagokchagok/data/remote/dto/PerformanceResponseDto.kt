package com.jth.chagokchagok.data.remote.dto


data class PerformanceResponseDto(
    val name: String,
    val attendingDate: String,     // ISO 포맷 문자열 e.g. "2025-07-08T19:45:00"
    val photoUrl: String             // "data:image/jpeg;base64,..."
)
