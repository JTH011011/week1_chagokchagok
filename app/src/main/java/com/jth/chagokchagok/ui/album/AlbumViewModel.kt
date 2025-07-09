package com.jth.chagokchagok.ui.album

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jth.chagokchagok.data.remote.api.PerformanceApi
import com.jth.chagokchagok.data.remote.dto.PerformanceResponseDetailDto
import com.jth.chagokchagok.ui.album.PerformanceApiProvider.api
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.jth.chagokchagok.data.remote.dto.PerformanceResponseDto

class AlbumViewModel() : ViewModel() {

    private val _photoUrls = MutableStateFlow<List<String>>(emptyList())
    val photoUrls: StateFlow<List<String>> = _photoUrls

    fun loadPhotoUrls(userId: String, year: Int, month: Int) {
        viewModelScope.launch {
            try {
                val api = PerformanceApiProvider.api
                val yearMonth = "$year-${"%02d".format(month)}"  // "202507" 같은 형식
                val response = api.getPerformancesWithPhotoUrls(userId, yearMonth)

                if (response.isSuccessful) {
                    val performances = response.body() ?: emptyList()
                    // DTO 리스트 중에서 photoUrl만 뽑아서 상태에 저장
                    val urls = performances.map { it.photoUrl }
                    _photoUrls.value = urls
                } else {
                    _photoUrls.value = emptyList()
                }
            } catch (e: Exception) {
                _photoUrls.value = emptyList()
            }
        }
    }
}
