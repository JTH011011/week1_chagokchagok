package com.jth.chagokchagok.util

// 이름 유효성 검사 (비어있지 않고, 한글/영문/숫자/공백 허용, 특수문자 제외)
fun isValidName(input: String): Boolean {
    return input.isNotBlank() && input.all {
        it.isLetter() || it in '가'..'힣' || it.isDigit() || it.isWhitespace()
    }
}

// 아이디 유효성 검사 (영문 + 숫자만 허용, 길이 4~20)
fun isValidId(input: String): Boolean {
    val regex = Regex("^[a-zA-Z0-9]{4,20}$")
    return regex.matches(input)
}

// 비밀번호 유효성 검사 (길이 8~20, ASCII 범위, 영문 + 숫자 + 특수문자 모두 포함)
fun isValidPassword(input: String): Boolean {
    if (input.length !in 8..20) return false
    val asciiOnly = input.all { it.code in 33..126 }
    val hasLetter = input.any { it.isLetter() }
    val hasDigit = input.any { it.isDigit() }
    val hasSpecial = input.any { "!@#$%^&*()_+-=[]{}|;':\",.<>?/".contains(it) }
    return asciiOnly && hasLetter && hasDigit && hasSpecial
}

// 로그인 입력 간단 검증 (빈칸 여부만 확인)
fun isLoginInputValid(id: String, password: String): Boolean {
    return id.isNotBlank() && password.isNotBlank()
}

