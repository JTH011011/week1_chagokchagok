package com.jth.chagokchagok.ui.addview

import android.content.ContentResolver
import android.util.Base64
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jth.chagokchagok.data.preferences.UserPreferences
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.jth.chagokchagok.navigation.BottomNavItem
import com.jth.chagokchagok.navigation.Screen
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

@Composable
fun AddViewScreen(
    navController: NavController,
    viewModel: AddViewViewModel = viewModel()
) {
    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }
    val userId = userPreferences.userIdFlow.collectAsState(initial = null).value ?: return

    val title = viewModel.title.collectAsState().value
    val genre = viewModel.genre.collectAsState().value
    val cast = viewModel.cast.collectAsState().value
    val seat = viewModel.seat.collectAsState().value
    val price = viewModel.price.collectAsState().value

    val year = viewModel.selectedYear.collectAsState().value
    val month = viewModel.selectedMonth.collectAsState().value
    val day = viewModel.selectedDay.collectAsState().value
    val photoUri = viewModel.photoUri.collectAsState().value
    val submissionResult = viewModel.submissionResult.collectAsState().value

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            viewModel.setPhotoUri(it) // ✅ 선택된 이미지 URI를 ViewModel에 전달
        }
    }

    LaunchedEffect(submissionResult) {
        if (submissionResult == true) {
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = { AddViewTopBar(navController) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { viewModel.title.value = it },
                label = { Text("제목") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DropdownMenuBox("년", year, 2020..2030) { viewModel.selectedYear.value = it }
                DropdownMenuBox("월", month, 1..12) { viewModel.selectedMonth.value = it }
                DropdownMenuBox("일", day, 1..31) { viewModel.selectedDay.value = it }
            }

            OutlinedTextField(
                value = genre,
                onValueChange = { viewModel.genre.value = it },
                label = { Text("종류") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = cast,
                onValueChange = { viewModel.cast.value = it },
                label = { Text("출연자") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = seat,
                onValueChange = { viewModel.seat.value = it },
                label = { Text("좌석") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = price,
                onValueChange = { viewModel.price.value = it },
                label = { Text("가격") },
                modifier = Modifier.fillMaxWidth()
            )

            Text("사진", fontWeight = FontWeight.Medium)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable { launcher.launch("image/*") },
                contentAlignment = Alignment.Center
            )

            {
                if (photoUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(photoUri),
                        contentDescription = "선택한 사진",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(
                        text = "클릭하여 사진 선택",
                        color = Color.Gray
                    )
                }


            }

            Button(
                onClick = {
                    viewModel.submit(userId, context) {
                        navController.navigate(BottomNavItem.Home.route) {
                            popUpTo(Screen.AddView.route) { inclusive = true } // AddView 화면 삭제
                            launchSingleTop = true
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = title.isNotBlank() && genre.isNotBlank() && price.isNotBlank() && photoUri != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (
                        title.isNotBlank() && genre.isNotBlank() && price.isNotBlank() && photoUri != null
                    ) Color(0xFFFF9800) else Color.LightGray,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("관람 기록 추가")
            }
        }
    }
}



@Composable
fun AddViewTopBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 24.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "뒤로가기"
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "관람 기록 추가",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}


@Composable
fun DropdownMenuBox(label: String, selected: Int, range: IntRange, onSelected: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text("$selected $label")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            range.forEach {
                DropdownMenuItem(
                    text = { Text(it.toString()) },
                    onClick = {
                        onSelected(it)
                        expanded = false
                    }
                )
            }
        }
    }
}
