package com.jth.chagokchagok

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import com.jth.chagokchagok.navigation.AppNavGraph
import com.jth.chagokchagok.ui.login.LoginViewModel
import com.jth.chagokchagok.ui.theme.ChagokchagokTheme

class MainActivity : ComponentActivity() {

    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChagokchagokTheme {
                val navController = rememberNavController()

                // ✅ Scaffold로 감싸서 진짜 innerPadding을 AppNavGraph에 넘김
                Scaffold { innerPadding ->
                    AppNavGraph(
                        navController = navController,
                        loginViewModel = loginViewModel,
                        innerPadding = innerPadding
                    )
                }
            }
        }
    }
}

