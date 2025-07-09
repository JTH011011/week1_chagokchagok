package com.jth.chagokchagok.ui.mypage

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.jth.chagokchagok.R
import com.jth.chagokchagok.data.preferences.UserPreferences
import com.jth.chagokchagok.data.repository.PerformanceRecord
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


@Composable
fun MyPageScreen(
    navController: NavController,
    viewModel: MyPageViewModel = viewModel(),
    userPreferences: UserPreferences
) {
    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }
    val userId = userPreferences.userIdFlow.collectAsState(initial = null).value
    val performanceList = viewModel.performances.collectAsState().value

    // 👉 userId 가져와서 loadPerformances에 넘겨주기
    LaunchedEffect(userId) {
        if (userId != null){
            viewModel.loadPerformances(userId)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        /* ── 1) 프로필 헤더 ───────────────────────────── */
        item {
            Spacer(Modifier.height(36.dp))
            HeaderSection(
                userId   = userId ?: "test123",
                onProfile  = { /* TODO */ },
                onBudget   = { navController.navigate(com.jth.chagokchagok.navigation.Screen.EditBudget.route) }
            )
        }

        /* ── 2) 최근 공연 기록 타이틀 ───────────────── */
        if (performanceList.isNotEmpty()) {
            item {
                Text(
                    "최근 공연 기록",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(8.dp))
            }
        }

        /* ── 3) 공연 리스트 ──────────────────────────── */
        items(performanceList) { perf ->
            PerformanceRow(perf)
        }

        item { Spacer(Modifier.height(80.dp)) }
    }
}

/* ───────── 개별 Row ───────── */
@Composable
private fun PerformanceRow(perf: PerformanceRecord) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val imgModifier = Modifier
            .size(64.dp)
            .clip(MaterialTheme.shapes.small)

        // 썸네일 or Placeholder
        if (!perf.thumbnailURL.isNullOrBlank()) {
            AsyncImage(
                model = perf.thumbnailURL,
                contentDescription = null,
                modifier = imgModifier
            )
        } else {
            Box(imgModifier.background(Color.LightGray))
        }

        Spacer(Modifier.width(16.dp))

        Column {
            Text(perf.title, fontWeight = FontWeight.Medium)
            Text(perf.date, fontSize = 13.sp, color = Color.Gray)
        }
    }
}


/* ───────── 헤더 섹션 ───────── */
@Composable
private fun HeaderSection(
    userId: String,
    onProfile: () -> Unit,
    onBudget:  () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "문화생활 기록",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(24.dp))

        Image(
            painter = painterResource(R.drawable.profile_dummy),
            contentDescription = null,
            modifier = Modifier.size(100.dp).clip(CircleShape)
        )

        Spacer(Modifier.height(16.dp))
        Text("$userId 님의 프로필", fontWeight = FontWeight.Medium, fontSize = 16.sp)
    }
    Spacer(Modifier.height(24.dp))

    Button(
        onClick = onProfile,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFF3E0)),
        modifier = Modifier.fillMaxWidth().height(48.dp)
    ) { Text("프로필 설정하기", color = Color.Black) }

    Spacer(Modifier.height(12.dp))

    Button(
        onClick = onBudget,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
        modifier = Modifier.fillMaxWidth().height(48.dp)
    ) { Text("예산 설정하기", color = Color.White) }
}
