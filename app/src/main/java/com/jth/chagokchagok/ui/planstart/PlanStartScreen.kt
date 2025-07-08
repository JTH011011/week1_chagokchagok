package com.jth.chagokchagok.ui.planstart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jth.chagokchagok.R
import com.jth.chagokchagok.ui.theme.ChagokchagokTheme
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun PlanStartScreen(
    onStartClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 32.dp)
            .verticalScroll(rememberScrollState()), // 스크롤 가능하도록 설정
    ) {
        // 오른쪽 상단 로고 (살짝 더 내려서 겹침 방지)
        Image(
            painter = painterResource(id = R.drawable.sunny_logo),
            contentDescription = "햇살 얼굴 로고",
            modifier = Modifier
                .size(width = 62.dp, height = 56.dp)
                .align(Alignment.TopEnd)
                .offset(x = (-15).dp, y = 120.dp) // ← 약간만 조정해서 글자와 어색하지 않게 분리
        )

        // 텍스트 블럭
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 160.dp), // ← 로고보다 아래로 조정
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                buildAnnotatedString {
                    append("문화생활도 저축처럼,\n")
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFFFF9800),
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("차곡차곡")
                    }
                    append("에 오신 것을 환영합니다!")
                },
                fontSize = 22.sp,
                color = Color.Black,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "차곡차곡 예산 계획부터 세워볼까요?",
                fontSize = 20.sp,
                color = Color.Black
            )
        }

        // 하단 버튼
        Surface(
            shape = RoundedCornerShape(16.dp),
            shadowElevation = 4.dp,
            color = Color(0xFFFFF3E0),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 96.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { onStartClick() }
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "계획 시작",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF9800)
                )
                Text(
                    text = " >",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF9800)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun planStartScreenPreview() {
    ChagokchagokTheme {
        PlanStartScreen()
    }
}

