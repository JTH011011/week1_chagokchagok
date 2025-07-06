package com.jth.chagokchagok.ui.addview

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun AddViewScreen(navController: NavController) {
    Button(onClick = { navController.popBackStack() }) {
        Text("뒤로가기 (홈으로)")
    }
}
