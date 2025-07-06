package com.jth.chagokchagok.util

// 이름 유효성 검사 (특수문자 제외, 공백/한글/영어/숫자 허용)
fun isValidName(input: String): Boolean {
    return input.all { it.isLetter() || it in '가'..'힣' || it.isDigit() }
}

// 아이디 유효성 검사 (회원가입에서만 사용)
fun isValidId(input: String): Boolean {
    return input.length in 4..20
}

// 비밀번호 유효성 검사 (회원가입에서 사용)
fun isValidPassword(input: String): Boolean {
    if (input.length !in 8..20) return false
    val asciiOnly = input.all { it.code in 33..126 }
    val hasLetter = input.any { it.isLetter() }
    val hasDigit = input.any { it.isDigit() }
    val hasSpecial = input.any { "!@#$%^&*()_+-=[]{}|;':\",.<>?/".contains(it) }
    val categoryCount = listOf(hasLetter, hasDigit, hasSpecial).count { it }
    return asciiOnly && categoryCount >= 2
}

// 로그인 입력 간단 검증 (빈칸 여부만 확인)
fun isLoginInputValid(id: String, password: String): Boolean {
    return id.isNotBlank() && password.isNotBlank()
}
