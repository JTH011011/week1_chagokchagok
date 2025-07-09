package com.jth.chagokchagok.data.remote.dto

import java.time.LocalDateTime

// ✅ 마이페이지 & 사진첩 리스트 조회용 DTO (가볍게 사용)
data class PerformanceResponseDto(
    val name: String,
    val attendingDate: String,     // ISO 포맷 문자열 e.g. "2025-07-08T19:45:00"
    val photoUrl: String           // 예: "http://.../uploads/abc.jpg"
)

// ✅ 상세 조회용 DTO (사진첩 상세조회 등)
data class PerformanceResponseDetailDto(
    val performanceId: Long,
    val attendingDate: LocalDateTime,
    val castInfo: String,
    val genre: String,
    val name: String,
    val photoUrl: String,         // photoPath → photoUrl 로 명확하게 변경
    val price: Int,
    val seat: String,
    val userId: String
)
