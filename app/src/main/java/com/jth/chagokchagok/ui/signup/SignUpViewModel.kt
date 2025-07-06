package com.jth.chagokchagok.ui.signup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.jth.chagokchagok.util.*

class SignUpViewModel : ViewModel() {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _id = MutableStateFlow("")
    val id: StateFlow<String> = _id

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    val isNameValid: Boolean
        get() = isValidName(_name.value)

    val isIdValid: Boolean
        get() = isValidId(_id.value)

    val isPasswordValid: Boolean
        get() = isValidPassword(_password.value)

    val isFormValid: Boolean
        get() = isNameValid && isIdValid && isPasswordValid

    fun onNameChanged(newName: String) {
        val filtered = newName.filter { it.isLetterOrDigit() || it in '가'..'힣' }

        // 글자수 제한: 한글 2자, 영문/숫자 1자로 계산
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


    fun onIdChanged(value: String) {
        _id.value = value.filter { it.isLowerCase() || it.isDigit() }
    }

    fun onPasswordChanged(value: String) {
        _password.value = value.filter { it.code in 33..126 }
    }
}
