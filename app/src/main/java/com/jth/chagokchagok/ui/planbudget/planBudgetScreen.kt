package com.jth.chagokchagok.ui.planbudget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun planBudgetScreen(
    onPreviousClick: () -> Unit,
    onCompleteClick: (Int) -> Unit
) {
    var selectedAmount by remember { mutableStateOf<Int?>(null) }
    var manualInput by remember { mutableStateOf("") }

    val parsedManualInput = manualInput.toIntOrNull()
    val isValid = (selectedAmount != null) || (parsedManualInput != null && parsedManualInput > 0)
    val finalAmount = selectedAmount ?: parsedManualInput

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(108.dp))

        Text(
            text = "한 달 예산 파악",
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(72.dp))

        Text("Q.", color = Color(0xFFFF9800), fontWeight = FontWeight.Bold)

        val userName = "추후에 백엔드" // TODO: 백엔드
        Text(
            text = "$userName 님,\n이번달 문화생활에\n얼마를 쓰고 싶으신가요?",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            lineHeight = 28.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text("A.", color = Color(0xFFFF9800), fontWeight = FontWeight.Bold)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            OutlinedTextField(
                value = manualInput,
                onValueChange = { inputText ->
                    manualInput = inputText.filter { it.isDigit() }
                    selectedAmount = null
                },
                label = { Text("1원부터 입력 가능") },
                modifier = Modifier
                    .weight(0.8f)
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 24.sp,
                    color = Color(0xFFFF9800)
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "원 이에요.",
                fontSize = 24.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf(100_000, 500_000, 1_000_000).forEach { amount ->
                val isSelected = selectedAmount == amount
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (isSelected) Color(0xFFFF9800) else Color(0xFFFFF3E0)
                        )
                        .clickable {
                            selectedAmount = amount
                            manualInput = amount.toString()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${amount / 10_000}만원",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = if (isSelected) Color.White else Color.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = onPreviousClick,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color(0xFFFF9800)),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFFF9800)
                ),
                modifier = Modifier
                    .weight(0.6f)
                    .height(50.dp)
            ) {
                Text("이전", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { finalAmount?.let { onCompleteClick(it) } },
                enabled = isValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isValid) Color(0xFFFF9800) else Color.LightGray
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .weight(0.6f)
                    .height(50.dp)
            ) {
                Text("완료", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

