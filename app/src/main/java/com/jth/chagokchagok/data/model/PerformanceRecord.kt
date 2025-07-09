package com.jth.chagokchagok.data.model

import java.time.LocalDateTime

data class PerformanceRecord(
    val performanceId: Long?,
    val userId: String,
    val name: String,
    val genre: String,
    val castInfo: String,
    val attendingDate: LocalDateTime,
    val seat: String,
    val price: Int,
    val photoUrl: String
)