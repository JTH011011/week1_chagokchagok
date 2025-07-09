package com.jth.chagokchagok.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jth.chagokchagok.data.remote.RetrofitProvider
import com.jth.chagokchagok.data.remote.dto.BudgetResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

data class HomeUiState(
    val budget: Int = 0,
    val spent: Int = 0,
    val viewCount: Int = 0
) {
    val remaining: Int
        get() = (budget - spent).coerceAtLeast(0)

    val progress: Float
        get() = if (budget == 0) 0f else spent.toFloat() / budget
}

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    private val _activityDates = MutableStateFlow<List<LocalDate>>(emptyList())
    val activityDates: StateFlow<List<LocalDate>> = _activityDates


    fun loadMonthlyBudget(userId: String, yearMonth: YearMonth = YearMonth.now()) {
        viewModelScope.launch {
            try {
                val response = RetrofitProvider.budgetApi.getBudget(
                    userId = userId,
                    yearMonth = yearMonth.toString() // "2025-07"
                )

                val performanceResponse = RetrofitProvider.performanceApi.getUserPerformances(userId)
                val performances = performanceResponse.body() ?: emptyList()

                val viewCount = performances.count {
                    it.attendingDate.startsWith(yearMonth.toString())
                }

                val activityDates = performances
                    .mapNotNull {
                        try {
                            LocalDateTime.parse(it.attendingDate).toLocalDate()
                        } catch (e: Exception) {
                            null
                        }
                    }
                    .filter {
                        it.year == yearMonth.year && it.monthValue == yearMonth.monthValue
                    }

                _activityDates.value = activityDates // "2025-07-01" 형식


                response.body()?.let { data ->
                    _uiState.value = HomeUiState(
                        budget = data.budget,
                        spent = data.spending,
                        viewCount = viewCount
                    )
                }

            } catch (e: Exception) {
                e.printStackTrace()
                println("💥 예산 불러오기 실패: ${e.message}")
            }

        }
    }
}



