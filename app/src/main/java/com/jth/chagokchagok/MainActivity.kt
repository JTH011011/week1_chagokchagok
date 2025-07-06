package com.jth.chagokchagok

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jth.chagokchagok.ui.login.LoginScreen
import com.jth.chagokchagok.ui.login.LoginViewModel
import com.jth.chagokchagok.ui.planbudget.planBudgetScreen
import com.jth.chagokchagok.ui.signup.SignUpScreen
import com.jth.chagokchagok.ui.theme.ChagokchagokTheme
import com.jth.chagokchagok.ui.planstart.planStartScreen


class MainActivity : ComponentActivity() {

    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChagokchagokTheme {
                val navController = rememberNavController()
                //!!!이 바로 아랫줄을 바꿔가면서 디버깅하면 됨!!!
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") {
                        val id by loginViewModel.id.collectAsState(initial = "")
                        val password by loginViewModel.password.collectAsState(initial = "")

                        LoginScreen(
                            id = id,
                            password = password,
                            onIdChanged = loginViewModel::onIdChanged,
                            onPasswordChanged = loginViewModel::onPasswordChanged,
                            onLoginClick = loginViewModel::onLoginClicked,
                            onSignUpClick = {
                                navController.navigate("signup")
                            }
                        )
                    }

                    composable("signup") {
                        SignUpScreen(
                            onSignUpComplete = {
                                navController.navigate("planstart")
                            },
                            onBackClick = {
                                navController.popBackStack() // 뒤로가기 버튼 클릭 시 로그인으로 돌아감
                            }
                        )
                    }

                    composable("planstart") {
                        planStartScreen(
                            onStartClick = {
                                navController.navigate("planbudget") // 시작 버튼 클릭 시 로그인 화면으로 이동
                            }
                        )
                    }

                    composable("planbudget") {
                        planBudgetScreen()
                    }
                }
            }
        }
    }
}
