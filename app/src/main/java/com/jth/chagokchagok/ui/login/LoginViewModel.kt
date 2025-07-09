package com.jth.chagokchagok.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jth.chagokchagok.data.remote.dto.LoginRequest
import com.jth.chagokchagok.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// 로그인 UI 상태 표현
sealed interface LoginUiState {
    object Idle : LoginUiState
    object Loading : LoginUiState
    data class Success(val username: String) : LoginUiState
    data class Error(val message: String) : LoginUiState
}

class LoginViewModel : ViewModel() {

    private val repository = UserRepository()

    private val _id = MutableStateFlow("")
    val id: StateFlow<String> = _id

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onIdChanged(newId: String) {
        _id.value = newId
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    fun onLoginClicked() {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            val request = LoginRequest(
                username = _id.value,
                password = _password.value
            )

            repository.login(request)
                .onSuccess {
                    _uiState.value = LoginUiState.Success(it.username)
                }
                .onFailure {
                    _uiState.value = LoginUiState.Error(it.message ?: "로그인 중 오류 발생")
                }
        }
    }
}
