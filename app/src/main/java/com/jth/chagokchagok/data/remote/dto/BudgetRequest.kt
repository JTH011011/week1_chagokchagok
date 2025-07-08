package com.jth.chagokchagok.data.remote.dto

import java.time.YearMonth

data class BudgetRequest(
    val userId: String,
    val yearMonth: String,  // 예: "2025-07"
    val budget: Int,
    val spending: Int = 0   // 기본값 0
)
