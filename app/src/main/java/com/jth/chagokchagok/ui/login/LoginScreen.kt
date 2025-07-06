package com.jth.chagokchagok.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jth.chagokchagok.ui.theme.ChagokchagokTheme
import com.jth.chagokchagok.util.isValidId
import com.jth.chagokchagok.util.isValidPassword

@Composable
fun LoginScreen(
    id: String,
    password: String,
    onIdChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 32.dp, vertical = 144.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "기억을 모으는 나만의 통장,\n",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
            color = Color.Black,
            lineHeight = 12.sp
        )
        Text(
            text = "차곡차곡",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            color = Color(0xFFFF9800)
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "로그인",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 아이디 입력
        OutlinedTextField(
            value = id,
            onValueChange = {
                onIdChanged(it.filter { ch -> ch.isLowerCase() || ch.isDigit() })
            },
            label = { Text("아이디") },
            isError = id.isNotEmpty() && !isValidId(id),
            supportingText = {
                if (id.isNotEmpty() && !isValidId(id)) {
                    Text("영문/숫자만 허용, 4~20자", fontSize = 12.sp, color = Color.Gray)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 비밀번호 입력
        OutlinedTextField(
            value = password,
            onValueChange = {
                onPasswordChanged(it.filter { ch -> ch.code in 33..126 })
            },
            label = { Text("비밀번호") },
            isError = password.isNotEmpty() && !isValidPassword(password),
            supportingText = {
                if (password.isNotEmpty() && !isValidPassword(password)) {
                    Text("8~20자, 영문/숫자/특수문자 조합", fontSize = 12.sp, color = Color.Gray)
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "비밀번호 보기/숨기기"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "비밀번호를 잊으셨나요?",
            color = Color(0xFFFF9800),
            fontSize = 13.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 4.dp),
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onLoginClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = isValidId(id) && isValidPassword(password)
        ) {
            Text("로그인", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "처음 오셨나요?",
            color = Color.Gray,
            fontSize = 13.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "회원가입",
            color = Color(0xFFFF9800),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable { onSignUpClick() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ChagokchagokTheme {
        LoginScreen(
            id = "",
            password = "",
            onIdChanged = {},
            onPasswordChanged = {},
            onLoginClick = {},
            onSignUpClick = {}
        )
    }
}

