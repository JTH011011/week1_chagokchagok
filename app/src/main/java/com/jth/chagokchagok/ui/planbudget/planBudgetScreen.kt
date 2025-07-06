// 경로: com/jth/chagokchagok/ui/planbudget/planBudgetScreen.kt
package com.jth.chagokchagok.ui.planbudget

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun planBudgetScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "예산 입력화면 (준비중)")
    }
}
