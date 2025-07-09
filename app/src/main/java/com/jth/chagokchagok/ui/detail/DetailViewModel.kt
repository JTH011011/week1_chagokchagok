package com.jth.chagokchagok.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.time.LocalDate
import com.jth.chagokchagok.data.repository.PerformanceRecord
import com.jth.chagokchagok.data.remote.dto.PerformanceResponseDto
import com.jth.chagokchagok.data.repository.PerformanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: PerformanceRepository
) : ViewModel() {

    private val _records = MutableStateFlow<List<PerformanceRecord>>(emptyList())
    val records: StateFlow<List<PerformanceRecord>> = _records

    fun loadRecordsForDate(userId: String, date: LocalDate) {
        viewModelScope.launch {
            try {
                val result = repository.getPerformancesByDate(userId, date)
                _records.value = result
            } catch (e: Exception) {
                e.printStackTrace()
                _records.value = emptyList()
            }
        }
    }
}