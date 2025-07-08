package com.jth.chagokchagok.data.remote.dto

import java.time.LocalDateTime

data class PerformanceResponseDTO (
        val performanceId: Long,
        val attendingDate: LocalDateTime, // 서버에서 LocalDateTime을 문자열로 내려주면 String으로 받음
        val castInfo: String,
        val genre: String,
        val name: String,
        val photo: String, // base64 or null
        val price: Int,
        val seat: String,
        val userId: String,
        val photoPath: String
    )
