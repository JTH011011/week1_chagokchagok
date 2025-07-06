package com.jth.chagokchagok.ui.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {

    // ID 입력 상태
    private val _id = MutableStateFlow("")
    val id: StateFlow<String> = _id

    // 비밀번호 입력 상태
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    // ID 변경 시 호출
    fun onIdChanged(newId: String) {
        _id.value = newId
    }

    // 비밀번호 변경 시 호출
    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    // 로그인 버튼 클릭 시 호출
    fun onLoginClicked() {
        // 추후 Firebase Auth 연결 예정
        println("로그인 시도: ID=${_id.value}, Password=${_password.value}")
    }
}
