package com.jth.chagokchagok.ui.mypage

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MyPageViewModel : ViewModel() {
    private val _userName = MutableStateFlow("나중에백엔드!") // TODO: 백엔드 연동 시 수정
    val userName: StateFlow<String> = _userName

    // 추후 API 연동 시
    fun loadUserInfo() {
        // 예: _userName.value = userRepository.getUserName()
    }
}
