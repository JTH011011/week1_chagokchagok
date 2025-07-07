// 📄 com.jth.chagokchagok.ui.editbudget.EditBudgetViewModel.kt
package com.jth.chagokchagok.ui.editbudget

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.YearMonth

/**
 * 예산 설정 화면 전용 ViewModel
 * - 선택된 연도와 월에 따라 월별 예산 저장/불러오기 지원
 * - 지출 금액과 남은 예산 계산 포함
 */
class EditBudgetViewModel : ViewModel() {
    private val _selectedYear = MutableStateFlow(YearMonth.now().year)
    private val _selectedMonth = MutableStateFlow(YearMonth.now().monthValue)

    private val _budget = MutableStateFlow(0)
    private val _spent = MutableStateFlow(100_000) // 예: 기본 지출

    // 월별 예산 저장용 (메모리 캐시)
    private val _monthlyBudgets = mutableMapOf<YearMonth, Int>()

    val selectedYear: StateFlow<Int> get() = _selectedYear
    val selectedMonth: StateFlow<Int> get() = _selectedMonth
    val budget: StateFlow<Int> get() = _budget
    val spent: StateFlow<Int> get() = _spent
    val remaining: Int get() = (_budget.value - _spent.value).coerceAtLeast(0)

    fun updateYear(year: Int) {
        _selectedYear.value = year
        loadBudgetForSelectedMonth()
    }

    fun updateMonth(month: Int) {
        _selectedMonth.value = month
        loadBudgetForSelectedMonth()
    }

    fun updateBudget(budget: Int) {
        val key = YearMonth.of(_selectedYear.value, _selectedMonth.value)
        _monthlyBudgets[key] = budget
        _budget.value = budget
    }

    private fun loadBudgetForSelectedMonth() {
        val key = YearMonth.of(_selectedYear.value, _selectedMonth.value)
        _budget.value = _monthlyBudgets[key] ?: 0
    }
}
