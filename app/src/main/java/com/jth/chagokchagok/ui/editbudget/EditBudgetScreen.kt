// 📄 com.jth.chagokchagok.ui.editbudget.EditBudgetScreen.kt
package com.jth.chagokchagok.ui.editbudget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.time.YearMonth


@Composable
fun EditBudgetScreen(
    navController: NavController,
    viewModel: EditBudgetViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var yearMenuOpen by remember { mutableStateOf(false) }
    var monthMenuOpen by remember { mutableStateOf(false) }

    val years = (YearMonth.now().year - 5)..(YearMonth.now().year + 1)
    val months = 1..12

    Scaffold(
        containerColor = Color.White,
        topBar = {
            BudgetTopBar(title = "예산 설정") {
                navController.popBackStack()
            }
        },
        bottomBar = {
            val isEnabled = viewModel.budget.collectAsState().value > 0
            Button(
                onClick = { /* TODO: backend 저장 후 popBackStack() */
                    navController.popBackStack()},
                enabled = isEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("수정하기", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(Modifier.height(64.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "예산",
                        fontSize = 16.sp,
                        color = Color(0xFF333333),
                        modifier = Modifier.weight(1f)
                    )

                    val budgetState = viewModel.budget.collectAsState()
                    var budgetInput by remember { mutableStateOf(budgetState.value.toString()) }

                    TextField(
                        value = budgetInput,
                        onValueChange = { newText ->
                            val filtered = newText.filter { it.isDigit() }
                            budgetInput = filtered
                            viewModel.updateBudget(filtered.toIntOrNull() ?: 0)
                        },
                        singleLine = true,
                        placeholder = { Text("0") },
                        trailingIcon = { Text("원", color = Color.Gray, fontSize = 16.sp) },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color(0xFFFF9800),
                            unfocusedIndicatorColor = Color(0xFFFFCC80),
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Gray
                        ),
                        modifier = Modifier
                            .weight(2f)
                    )
                }
            }


            Spacer(Modifier.height(32.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(onClick = { yearMenuOpen = true }) {
                    Text(
                        "${viewModel.selectedYear.collectAsState().value}년",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF9800)
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "연도 선택",
                        tint = Color(0xFFFF9800),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                DropdownMenu(expanded = yearMenuOpen, onDismissRequest = { yearMenuOpen = false }) {
                    years.forEach { y ->
                        DropdownMenuItem(text = { Text("${y}년") }, onClick = {
                            viewModel.updateYear(y)
                            yearMenuOpen = false
                        })
                    }
                }

                Spacer(Modifier.width(8.dp))

                TextButton(onClick = { monthMenuOpen = true }) {
                    Text(
                        "${viewModel.selectedMonth.collectAsState().value}월",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF9800)
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "월 선택",
                        tint = Color(0xFFFF9800),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                DropdownMenu(expanded = monthMenuOpen, onDismissRequest = { monthMenuOpen = false }) {
                    months.forEach { m ->
                        DropdownMenuItem(text = { Text("${m}월") }, onClick = {
                            viewModel.updateMonth(m)
                            monthMenuOpen = false
                        })
                    }
                }
            }

            Spacer(Modifier.height(32.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text("이번 달 현황", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF666666))
                Spacer(Modifier.height(12.dp))
                BudgetRow("이번 달 예산", viewModel.budget.collectAsState().value)
                BudgetRow("현재 지출", viewModel.spent.collectAsState().value)
                BudgetRow("남은 예산", viewModel.remaining)
            }
        }
    }
}


@Composable
fun BudgetTopBar(title: String, onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(108.dp)
            .statusBarsPadding()
            .padding(top = 48.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
        }
        Spacer(Modifier.width(8.dp))
        Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
    }
}

@Composable
private fun BudgetRow(label: String, amount: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 14.sp, color = Color(0xFF333333))
        Text("%,d 원".format(amount), fontSize = 14.sp, color = Color(0xFF333333))
    }
}
