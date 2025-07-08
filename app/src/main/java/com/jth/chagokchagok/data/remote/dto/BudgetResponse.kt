package com.jth.chagokchagok.data.remote.dto

import java.time.YearMonth

data class BudgetResponse(
    val id: Long,
    val userId: String,
    val yearMonth: String,
    val budget: Int,
    val spending: Int,
    val remaining: Int
){
    fun getYearMonth(): java.time.YearMonth = java.time.YearMonth.parse(yearMonth)
}
