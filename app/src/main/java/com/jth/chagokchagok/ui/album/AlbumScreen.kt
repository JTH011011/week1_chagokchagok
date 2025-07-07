package com.jth.chagokchagok.ui.album

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.jth.chagokchagok.navigation.Screen

@Composable
fun AlbumScreen(navController: NavController) {
    Button(onClick = {
        navController.navigate(Screen.MainShell.route) {
            // 백스택 정리: Album → MainShell만 남기기
            popUpTo(Screen.MainShell.route) { inclusive = true }
            launchSingleTop = true
        }
    }) {
        Text("홈으로 돌아가기")
    }
}
