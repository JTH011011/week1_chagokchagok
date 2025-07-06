package com.jth.chagokchagok.ui.mypage

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.jth.chagokchagok.navigation.Screen

@Composable
fun MyPageScreen(navController: NavController) {
    Button(onClick = { navController.navigate(Screen.Home.route) }) {
        Text("홈으로 돌아가기")
    }
}
