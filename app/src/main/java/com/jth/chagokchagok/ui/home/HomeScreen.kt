package com.jth.chagokchagok.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoAlbum
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jth.chagokchagok.R
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.DayPosition
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedDate = remember { mutableStateOf(LocalDate.now()) }
    val coroutineScope = rememberCoroutineScope()

    val calendarState = rememberCalendarState(
        startMonth = YearMonth.now().minusMonths(12),
        endMonth = YearMonth.now().plusMonths(12),
        firstVisibleMonth = YearMonth.now()
    )

    val activityDates = listOf(
        LocalDate.of(2025, 7, 2),
        LocalDate.of(2025, 7, 5),
        LocalDate.of(2025, 7, 10),
        LocalDate.of(2025, 7, 1),
        LocalDate.of(2025, 7, 7)
    )

    val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

    val contentWidth = Modifier
        .fillMaxWidth()
        .padding(horizontal = 28.dp)

    Scaffold(
        topBar = { HomeTopBar() },
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                icon = { Icon(Icons.Default.Add, contentDescription = "Add")},
                text = {
                    Text(
                        "관람 추가하기",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                },
                containerColor = Color(0xFFFF9800),
                onClick = { navController.navigate("addview") }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(Modifier.height(16.dp))

            // ── 예산 카드
            Row(
                modifier = contentWidth,
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                StatCard(
                    title = "남은 예산",
                    value = "${uiState.remaining} 원",
                    backgroundColor = Color(0xFFFFF4E1),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "이번 달 관람 횟수",
                    value = "${uiState.viewCount} 회",
                    backgroundColor = Color(0xFFFFE9C6),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(24.dp))

            ProgressCard(
                progress = uiState.progress,
                modifier = contentWidth,
                onEditClick = { navController.navigate("editbudget") }
            )

            Spacer(Modifier.height(32.dp))

            CalendarHeader(calendarState) { newMonth ->
                coroutineScope.launch { calendarState.animateScrollToMonth(newMonth) }
            }

            Spacer(Modifier.height(4.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                daysOfWeek.forEach {
                    Text(
                        it,
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(Modifier.height(4.dp))

            HorizontalCalendar(
                state = calendarState,
                dayContent = { dayState ->
                    val isSelected = dayState.date == selectedDate.value
                    val isCurrentMonth = dayState.position == DayPosition.MonthDate
                    val isActivityDate = dayState.date in activityDates

                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .padding(2.dp)
                            .border(0.5.dp, Color.LightGray)
                            .clickable { selectedDate.value = dayState.date }
                    ) {
                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .background(Color(0xFFFF9800).copy(alpha = 0.15f))
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                dayState.date.dayOfMonth.toString(),
                                color = if (isCurrentMonth) Color.Black else Color.Gray,
                                fontSize = 12.sp
                            )

                            if (isActivityDate) {
                                Box(
                                    modifier = Modifier
                                        .padding(top = 4.dp)
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFFF9800))
                                )
                            }
                        }
                    }
                },
                monthHeader = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .weight(1f)
            )
        }
    }
}

/* ────────── UI 컴포저블 ────────── */
@Composable
fun HomeTopBar() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 24.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.sunny_logo),
            contentDescription = "sunny logo",
            modifier = Modifier.size(32.dp)
        )
        Spacer(Modifier.width(6.dp))
        Text("차곡", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text("차곡", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFFFF9800))
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = modifier
            .height(76.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                title,
                fontSize = 14.sp,
                color = Color(0xFF444444),
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = value.filter { it.isDigit() },
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color(0xFFFF9800)
                )
                Text(
                    text = value.filter { !it.isDigit() },
                    fontSize = 17.sp,
                    color = Color(0xFF444444),
                    modifier = Modifier.padding(start = 2.dp)
                )
            }
        }
    }
}

@Composable
fun StripedProgressBar(
    progress: Float,                 // 0.0f – 1.0f
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFFFFF3E0),
    stripeColor: Color = Color(0xFFFF9800),
    stripeWidth: Dp = 12.dp,
    stripeSpacing: Dp = 6.dp,
    cornerRadius: Dp = 7.dp
) {
    // 트랙(배경)
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(backgroundColor)
            .height(14.dp)
    ) {
        // 진행 부분(빗금 영역)
        Canvas(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress)            // 진행 비율만큼 채우기
        ) {
            val density = LocalDensity
            val stripeW = with(density) { stripeWidth.toPx() }
            val stripeGap = with(density) { stripeSpacing.toPx() }
            val totalStripe = stripeW + stripeGap
            var startX = -size.height             // 대각선 시작 오프셋

            while (startX < size.width) {
                drawLine(
                    color = stripeColor,
                    start = Offset(startX, 0f),
                    end = Offset(startX + size.height, size.height),
                    strokeWidth = stripeW
                )
                startX += totalStripe
            }
        }
    }
}

/* ────────────────────────────────────────────────────────────────────────────
   ProgressCard – 위 요구 사항을 모두 반영한 카드 컴포저블
   ────────────────────────────────────────────────────────────────────────── */
@Composable
private fun ProgressCard(
    progress: Float,
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit = {}
) {
    Card(
        border = BorderStroke(1.dp, Color(0xFFFF9800)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
    ) {
        Column(Modifier.padding(16.dp)) {

            /* ── 헤더: 가운데 제목 + 우측 톱니 아이콘 ── */
            Box(Modifier.fillMaxWidth()) {
                Text(
                    text = "이번 달 예산 소비율",
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    color = Color(0xFF333333),
                    modifier = Modifier.align(Alignment.Center)
                )
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "설정",
                    tint = Color(0xFFFF9800),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable { onEditClick() }
                )
            }

            Spacer(Modifier.height(16.dp))

            /* ── 빗금형 진행 바 ── */
            StripedProgressBar(
                progress = progress,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            /* ── 퍼센트 (오른쪽 아래) ── */
            Text(
                text = "${(progress * 100).toInt()} %",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF9800),
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
fun CalendarHeader(
    calendarState: CalendarState,
    onMonthChanged: (YearMonth) -> Unit
) {
    val visibleMonth = calendarState.firstVisibleMonth.yearMonth

    var expandedYear by remember { mutableStateOf(false) }
    var expandedMonth by remember { mutableStateOf(false) }

    val years = (2020..2026).toList()
    val months = (1..12).toList()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // ── 년도 + 드롭다운 버튼 ──
            Text(
                text = "${visibleMonth.year}년",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFFFF9800),
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "연도 선택",
                tint = Color(0xFFFF9800),
                modifier = Modifier
                    .size(24.dp)
                    .clickable { expandedYear = true }
            )
            DropdownMenu(
                expanded = expandedYear,
                onDismissRequest = { expandedYear = false }
            ) {
                years.forEach { year ->
                    DropdownMenuItem(
                        text = { Text("$year 년") },
                        onClick = {
                            expandedYear = false
                            onMonthChanged(YearMonth.of(year, visibleMonth.monthValue))
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // ── 월 + 드롭다운 버튼 ──
            Text(
                text = "${visibleMonth.monthValue}월",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFFFF9800),
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "월 선택",
                tint = Color(0xFFFF9800),
                modifier = Modifier
                    .size(24.dp)
                    .clickable { expandedMonth = true }
            )
            DropdownMenu(
                expanded = expandedMonth,
                onDismissRequest = { expandedMonth = false }
            ) {
                months.forEach { month ->
                    DropdownMenuItem(
                        text = { Text("$month 월") },
                        onClick = {
                            expandedMonth = false
                            onMonthChanged(YearMonth.of(visibleMonth.year, month))
                        }
                    )
                }
            }
        }

        // ── 좌/우 월 이동 버튼 ──
        Row {
            Text(
                text = "<",
                fontSize = 32.sp,
                color = Color(0xFFFF9800),
                modifier = Modifier
                    .clickable { onMonthChanged(visibleMonth.minusMonths(1)) }
                    .padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = ">",
                fontSize = 32.sp,
                color = Color(0xFFFF9800),
                modifier = Modifier
                    .clickable { onMonthChanged(visibleMonth.plusMonths(1)) }
                    .padding(horizontal = 8.dp)
            )
        }
    }
}



