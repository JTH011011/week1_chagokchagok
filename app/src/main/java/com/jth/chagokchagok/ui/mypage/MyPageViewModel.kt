package com.jth.chagokchagok.ui.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MyPageViewModel : ViewModel() {
    private val _userName = MutableStateFlow("lovely") // 초기값
    val userName: StateFlow<String> = _userName

    // API 연동 시 사용할 함수
    fun loadUserInfo() {
        viewModelScope.launch {
            // TODO: 백엔드 연동하여 사용자 이름 받아오기
            // 예시:
            // val result = userRepository.getUserInfo()
            // _userName.value = result.name
        }
    }
}
