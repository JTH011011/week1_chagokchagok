package com.jth.chagokchagok.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jth.chagokchagok.data.remote.dto.SignupRequest
import com.jth.chagokchagok.data.repository.UserRepository
import com.jth.chagokchagok.util.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// UI 상태 표현
sealed interface SignUpUiState {
    object Idle : SignUpUiState
    object Loading : SignUpUiState
    data class Success(val message: String) : SignUpUiState
    data class Error(val message: String) : SignUpUiState
}

class SignUpViewModel : ViewModel() {

    // 사용자 입력값 상태
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _id = MutableStateFlow("")
    val id: StateFlow<String> = _id

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    // 유효성 검사 결과
    val isNameValid: Boolean
        get() = isValidName(_name.value)

    val isIdValid: Boolean
        get() = isValidId(_id.value)

    val isPasswordValid: Boolean
        get() = isValidPassword(_password.value)

    val isFormValid: Boolean
        get() = isNameValid && isIdValid && isPasswordValid

    // 회원가입 요청 상태
    private val _uiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Idle)
    val uiState: StateFlow<SignUpUiState> = _uiState

    private val repository = UserRepository()

    // 회원가입 요청
    fun requestSignUp() {
        if (!isFormValid) return

        viewModelScope.launch {
            _uiState.value = SignUpUiState.Loading

            val request = SignupRequest(
                username = _id.value,
                password = _password.value,
                name = _name.value
            )

            repository.signUp(request)
                .onSuccess {
                    _uiState.value = SignUpUiState.Success(it.message)
                }
                .onFailure {
                    _uiState.value = SignUpUiState.Error(it.message ?: "오류가 발생했습니다")
                }
        }
    }

    // 이름 입력 처리 (한글 2자, 영문/숫자 1자 기준 10글자 제한)
    fun onNameChanged(newName: String) {
        val filtered = newName.filter { it.isLetterOrDigit() || it in '가'..'힣' }

        var lengthCount = 0
        val result = StringBuilder()

        for (ch in filtered) {
            val unit = if (ch in '가'..'힣') 2 else 1
            if (lengthCount + unit > 10) break
            result.append(ch)
            lengthCount += unit
        }

        _name.value = result.toString()
    }

    // 아이디 입력 처리 (소문자/숫자만 허용)
    fun onIdChanged(value: String) {
        _id.value = value.filter { it.isLowerCase() || it.isDigit() }
    }

    // 비밀번호 입력 처리 (ASCII 33~126 범위 문자만 허용)
    fun onPasswordChanged(value: String) {
        _password.value = value.filter { it.code in 33..126 }
    }
}
