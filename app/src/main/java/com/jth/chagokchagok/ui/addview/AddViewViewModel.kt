package com.jth.chagokchagok.ui.addview

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jth.chagokchagok.data.remote.RetrofitProvider
import com.jth.chagokchagok.data.remote.dto.PerformanceRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AddViewViewModel : ViewModel() {

    val title = MutableStateFlow("")
    val genre = MutableStateFlow("")
    val cast = MutableStateFlow("")
    val seat = MutableStateFlow("")
    val price = MutableStateFlow("")

    val selectedYear = MutableStateFlow(LocalDate.now().year)
    val selectedMonth = MutableStateFlow(LocalDate.now().monthValue)
    val selectedDay = MutableStateFlow(LocalDate.now().dayOfMonth)

    val photoUri = MutableStateFlow<Uri?>(null)          // ✅ 선택된 이미지 URI
    val photoUrl = MutableStateFlow<String?>(null)       // ✅ 서버 업로드 후 URL

    private val _submissionResult = MutableStateFlow<Boolean?>(null)
    val submissionResult: StateFlow<Boolean?> = _submissionResult

    fun setPhotoUri(uri: Uri) {
        photoUri.value = uri
    }

    fun submit(userId: String, context: Context, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                // ✅ Step 1: 이미지 업로드
                val uri = photoUri.value
                if (uri != null && photoUrl.value == null) {
                    val file = uriToFile(context, uri)
                    val part = MultipartBody.Part.createFormData(
                        name = "image",
                        filename = file.name,
                        body = file.asRequestBody("image/*".toMediaType())
                    )

                    val uploadResponse = RetrofitProvider.performanceApi.uploadImage(part)
                    if (uploadResponse.isSuccessful) {
                        photoUrl.value = uploadResponse.body()?.url
                    } else {
                        val errorMsg = uploadResponse.errorBody()?.string()
                        Log.e("UploadError", "업로드 실패 이유: $errorMsg")  // ← 서버에서 던진 메시지 출력
                        _submissionResult.value = false
                        return@launch
                    }
                }

                // ✅ Step 2: 공연 정보 등록
                val date = LocalDate.of(selectedYear.value, selectedMonth.value, selectedDay.value)
                    .atStartOfDay()
                    .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

                val request = PerformanceRequest(
                    userId = userId,
                    name = title.value,
                    genre = genre.value,
                    cast = cast.value,
                    attendingDate = date,
                    seat = seat.value,
                    price = price.value.toIntOrNull() ?: 0,
                    photoUrl = photoUrl.value ?: ""
                )

                val response = RetrofitProvider.performanceApi.createPerformance(request)
                _submissionResult.value = response.isSuccessful
                if (response.isSuccessful) onSuccess()

            } catch (e: Exception) {
                e.printStackTrace()
                _submissionResult.value = false
            }
        }
    }

    // ✅ 유틸: Uri → File 변환
    private fun uriToFile(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)!!
        val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir)
        tempFile.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }
        return tempFile
    }
}
