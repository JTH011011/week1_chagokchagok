package com.jth.chagokchagok.data.remote.dto

data class BudgetResponse(
    val id: Long,
    val userId: String,
    val yearMonth: String,
    val budget: Int,
    val spending: Int,
    val remaining: Int
)
