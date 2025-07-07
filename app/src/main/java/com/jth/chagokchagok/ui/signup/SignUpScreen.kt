package com.jth.chagokchagok.ui.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jth.chagokchagok.ui.theme.ChagokchagokTheme
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect




@Composable
fun SignUpScreen(
    onSignUpComplete: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: SignUpViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // ✅ 상태 변화 감지 → 성공/실패 처리
    when (val state = uiState) {
        is SignUpUiState.Success -> {
            LaunchedEffect(state) {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                onSignUpComplete()
            }
        }
        is SignUpUiState.Error -> {
            LaunchedEffect(state) {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
        }
        else -> {}
    }

    val name by viewModel.name.collectAsState()
    val id by viewModel.id.collectAsState()
    val password by viewModel.password.collectAsState()
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 32.dp, vertical = 108.dp),
        verticalArrangement = Arrangement.Top
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(vertical = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "뒤로가기"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "회원가입",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = viewModel::onNameChanged,
            label = { Text("이름") },
            isError = name.isNotEmpty() && !viewModel.isNameValid,
            supportingText = {
                when {
                    name.isNotEmpty() && !viewModel.isNameValid -> {
                        Text("한글, 영어, 숫자만 허용 (공백/특수문자 제외)", fontSize = 12.sp, color = Color.Gray)
                    }
                    name.isNotEmpty() -> {
                        Text("한글 최대 5자 / 영문·숫자 최대 10자까지 입력 가능", fontSize = 12.sp, color = Color.Gray)
                    }
                    else -> {}
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = id,
            onValueChange = viewModel::onIdChanged,
            label = { Text("아이디") },
            isError = id.isNotEmpty() && !viewModel.isIdValid,
            supportingText = {
                if (id.isNotEmpty() && !viewModel.isIdValid) {
                    Text("4~20자, 영문, 숫자만 허용", fontSize = 12.sp, color = Color.Gray)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = viewModel::onPasswordChanged,
            label = { Text("비밀번호") },
            isError = password.isNotEmpty() && !viewModel.isPasswordValid,
            supportingText = {
                if (password.isNotEmpty() && !viewModel.isPasswordValid) {
                    Text("영문, 숫자, 특수문자 포함, 8~20자", fontSize = 12.sp, color = Color.Gray)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "비밀번호 보기/숨기기"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                viewModel.requestSignUp()
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = viewModel.isFormValid
        ) {
            Text("회원가입 완료", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    ChagokchagokTheme {
        SignUpScreen(
            onSignUpComplete = {},
            onBackClick = {}
        )
    }
}
