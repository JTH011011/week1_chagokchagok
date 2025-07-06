package com.jth.chagokchagok.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class HomeUiState(
    val budget: Int = 100_000,      // 전체 예산
    val spent: Int = 75_000         // 실험용 기본 지출값: 35%
) {
    val remaining: Int
        get() = (budget - spent).coerceAtLeast(0)

    val viewCount: Int = 0          // 관람 횟수는 추후 계산

    val progress: Float
        get() = if (budget == 0) 0f else spent.toFloat() / budget
}

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    fun loadInitialData(budget: Int, spent: Int) {
        _uiState.value = HomeUiState(budget, spent)
    }
}



