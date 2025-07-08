package com.jth.chagokchagok.ui.album

import androidx.compose.foundation.Image
import androidx.compose.material3.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.jth.chagokchagok.navigation.BottomNavItem.Companion.items
import com.jth.chagokchagok.navigation.Screen
import com.jth.chagokchagok.ui.album.AlbumViewModel
import java.time.LocalDate
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.jth.chagokchagok.R
import com.jth.chagokchagok.ui.home.HomeTopBar
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jth.chagokchagok.data.preferences.UserPreferences


@Composable
fun AlbumScreen(navController: NavController, viewModel: AlbumViewModel = viewModel()) {
    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }
    val userId = userPreferences.userIdFlow.collectAsState(initial = null).value ?: return

    val now = LocalDate.now()
    var selectedYear by remember { mutableStateOf(now.year) }
    var selectedMonth by remember { mutableStateOf(now.monthValue) }

    val photoUrls by viewModel.photoUrls.collectAsState()

    LaunchedEffect(photoUrls) {
        println("AlbumScreen photoUrls: $photoUrls")
    }

    // 선택 변경 시 사진 불러오기
    LaunchedEffect(userId, selectedYear, selectedMonth) {
        if (userId != null) {
            viewModel.loadPhotoUrls(userId, selectedYear, selectedMonth)
        }
    }

    Scaffold(
        topBar = { AlbumTopBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)  // Scaffold가 준 패딩 적용
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            AlbumYearMonthSelector(
                selectedYear = selectedYear,
                selectedMonth = selectedMonth,
                onYearSelected = { selectedYear = it },
                onMonthSelected = { selectedMonth = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize()
            ) {
                items(photoUrls) { url ->
                    Image(
                        painter = rememberAsyncImagePainter(url),
                        contentDescription = null,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@Composable
fun AlbumTopBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.sunny_logo),
                contentDescription = "sunny logo",
                modifier = Modifier.size(32.dp)
            )
            Spacer(Modifier.width(6.dp))
            Text("차곡", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("차곡", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFFFF9800))
        }
        Spacer(Modifier.height(8.dp))
        Text(
            "사진 모아보기",
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = Color(0xFF333333),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}



@Composable
fun AlbumYearMonthSelector(
    selectedYear: Int,
    selectedMonth: Int,
    onYearSelected: (Int) -> Unit,
    onMonthSelected: (Int) -> Unit,
) {
    val now = LocalDate.now()
    val yearList = (2020..now.year).toList().reversed()
    val monthList = (1..12).toList()

    var yearExpanded by remember { mutableStateOf(false) }
    var monthExpanded by remember { mutableStateOf(false) }

    Row {
        // ▼ 연도 선택
        Box {
            Row(
                modifier = Modifier
                    .clickable { yearExpanded = true }
                    .padding(8.dp)
            ) {
                Text(
                    text = "${selectedYear}년",
                    color = Color(0xFFFF9800),
                    style = MaterialTheme.typography.bodyLarge
                )
                Icon(
                    painter = painterResource(id = R.drawable.down_arrow),
                    contentDescription = "연도 선택",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .width(16.dp)
                        .height(16.dp)
                )
            }

            DropdownMenu(
                expanded = yearExpanded,
                onDismissRequest = { yearExpanded = false }
            ) {
                yearList.forEach { year ->
                    DropdownMenuItem(
                        text = { Text("${year}년") },
                        onClick = {
                            onYearSelected(year)
                            yearExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // ▼ 월 선택
        Box {
            Row(
                modifier = Modifier
                    .clickable { monthExpanded = true }
                    .padding(8.dp)
            ) {
                Text(
                    text = "${selectedMonth}월",
                    color = Color(0xFFFF9800),
                    style = MaterialTheme.typography.bodyLarge
                )
                Icon(
                    painter = painterResource(id = R.drawable.down_arrow),
                    contentDescription = "월 선택",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .width(16.dp)
                        .height(16.dp)
                )
            }

            DropdownMenu(
                expanded = monthExpanded,
                onDismissRequest = { monthExpanded = false }
            ) {
                monthList.forEach { month ->
                    DropdownMenuItem(
                        text = { Text("${month}월") },
                        onClick = {
                            onMonthSelected(month)
                            monthExpanded = false
                        }
                    )
                }
            }
        }
    }
}