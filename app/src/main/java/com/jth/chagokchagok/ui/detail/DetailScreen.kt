package com.jth.chagokchagok.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import java.time.LocalDate
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.runtime.getValue

import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.sp
import com.jth.chagokchagok.data.repository.PerformanceRecord


@Composable
fun DetailScreen(
    navController: NavController,
    userId: String,
    date: LocalDate,
    viewModel: DetailViewModel = viewModel()
) {
    // 예: date에 해당하는 관람 기록 목록 로딩
    val records by viewModel.records.collectAsState()

    LaunchedEffect(userId, date) {
        viewModel.loadRecordsForDate(userId, date)
    }

    Scaffold(
        topBar = {
            Column {
                // 상단 바
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .clickable { navController.popBackStack() }
                            .size(24.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "관람 기록", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.weight(1f))
                }

                // 날짜 텍스트
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${date.year}년 ${date.monthValue}월 ${date.dayOfMonth}일",
                        color = androidx.compose.ui.graphics.Color(0xFFFF9800), // 노란색
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(text = " 의 관람 기록", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // 위아래 스크롤 가능
        ) {
            records.forEach { record ->
                // 사진이나 관람기록 UI 표시
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(record.thumbnailURL),
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            Text(text = record.title, fontWeight = FontWeight.Bold)
                            Text(text = record.date.toString()) // 또는 원하는 형식으로 포맷
                        }
                    }
                }
            }
        }
    }
}