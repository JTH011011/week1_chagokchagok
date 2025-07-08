package com.jth.chagokchagok.ui.editbudget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jth.chagokchagok.data.remote.RetrofitProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.YearMonth

// UI 상태를 담는 데이터 클래스
data class EditBudgetUiState(
    val budget: Int = 0,
    val spent: Int = 0
) {
    val remaining: Int
        get() = (budget - spent).coerceAtLeast(0)
}

class EditBudgetViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(EditBudgetUiState())
    val uiState: StateFlow<EditBudgetUiState> = _uiState

    private val _selectedYear = MutableStateFlow(YearMonth.now().year)
    private val _selectedMonth = MutableStateFlow(YearMonth.now().monthValue)

    val selectedYear: StateFlow<Int> = _selectedYear
    val selectedMonth: StateFlow<Int> = _selectedMonth

    val budget: StateFlow<Int> get() = MutableStateFlow(_uiState.value.budget)
    val spent: StateFlow<Int> get() = MutableStateFlow(_uiState.value.spent)
    val remaining: Int get() = _uiState.value.remaining

    fun updateYear(year: Int) {
        _selectedYear.value = year
        // budget은 외부에서 새로 불러줘야 함
    }

    fun updateMonth(month: Int) {
        _selectedMonth.value = month
        // budget은 외부에서 새로 불러줘야 함
    }

    fun loadMonthlyBudget(userId: String, yearMonth: YearMonth) {
        viewModelScope.launch {
            try {
                val response = RetrofitProvider.budgetApi.getBudget(
                    userId = userId,
                    yearMonth = yearMonth.toString()
                )

                response.body()?.let { data ->
                    _uiState.value = EditBudgetUiState(
                        budget = data.budget,
                        spent = data.spending
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                println("\uD83D\uDCA5 EditBudget 예산 불러오기 실패: ${e.message}")
            }
        }
    }

    fun updateBudget(newBudget: Int) {
        _uiState.value = _uiState.value.copy(budget = newBudget)
    }
}
