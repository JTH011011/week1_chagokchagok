package com.jth.chagokchagok.ui.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jth.chagokchagok.data.remote.RetrofitProvider
import com.jth.chagokchagok.data.repository.PerformanceRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MyPageViewModel : ViewModel() {

    private val _performances = MutableStateFlow<List<PerformanceRecord>>(emptyList())
    val performances: StateFlow<List<PerformanceRecord>> = _performances

    fun loadPerformances(userId: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitProvider.performanceApi.getUserPerformances(userId)

                if (response.isSuccessful) {
                    val body = response.body() ?: emptyList()

                    val result = body
                        .sortedByDescending { LocalDateTime.parse(it.attendingDate) }
                        .take(10)
                        .map {
                            PerformanceRecord(
                                title = it.name,
                                date = LocalDateTime.parse(it.attendingDate).toLocalDate().toString(),
                                thumbnailURL = it.photoUrl  // ✅ 새 구조에 맞게 수정
                            )
                        }

                    _performances.value = result
                } else {
                    _performances.value = emptyList()
                }

            } catch (e: Exception) {
                e.printStackTrace()
                _performances.value = emptyList()
            }
        }
    }

}
