package com.jth.chagokchagok.data.repository

import com.jth.chagokchagok.data.remote.api.PerformanceApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class PerformanceRepository(
    private val api: PerformanceApi
) {

    suspend fun fetchRecentPerformances(userId: String, limit: Int = 10): List<PerformanceRecord> {
        return withContext(Dispatchers.IO) {
            val response = api.getUserPerformances(userId)
            if (response.isSuccessful) {
                response.body()
                    ?.sortedByDescending { LocalDateTime.parse(it.attendingDate) }
                    ?.take(limit)
                    ?.map { dto ->
                        PerformanceRecord(
                            title = dto.name,
                            date = LocalDateTime.parse(dto.attendingDate).toLocalDate().toString(),
                            thumbnailURL = dto.photoUrl  // ← 변경된 필드 이름
                        )
                    } ?: emptyList()
            } else {
                emptyList()
            }
        }
    }

}

data class PerformanceRecord(
    val title: String,
    val date: String,
    val thumbnailURL: String
)
