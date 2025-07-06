package com.jth.chagokchagok

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
                AppNavGraph(
                    navController = navController,
                    loginViewModel = loginViewModel
                )
            }
        }
    }
}

