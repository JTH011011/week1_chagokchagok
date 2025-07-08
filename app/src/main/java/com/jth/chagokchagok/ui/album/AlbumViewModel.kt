package com.jth.chagokchagok.ui.album

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jth.chagokchagok.data.remote.api.PerformanceApi
import com.jth.chagokchagok.data.remote.dto.PerformanceResponseDTO
import com.jth.chagokchagok.ui.album.PerformanceApiProvider.api
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AlbumViewModel : ViewModel() {

    private val _photoUrls = MutableStateFlow<List<String>>(emptyList())
    val photoUrls: StateFlow<List<String>> = _photoUrls

    fun loadPhotoUrls(year: Int, month: Int) {
        viewModelScope.launch {
            try {
                // PerformanceApiProvider.api.getPhotoUrlsByYearMonth(year, month)와 같은 함수 필요
                val api = PerformanceApiProvider.api
                val result = api.getPhotoUrlsByYearMonth(year, month)
                _photoUrls.value = result
            } catch (e: Exception) {
                _photoUrls.value = emptyList()
            }
        }
    }

}

