package com.jth.chagokchagok.data.repository

import com.jth.chagokchagok.data.remote.api.PerformanceApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import com.jth.chagokchagok.data.remote.dto.PerformanceResponseDto
import java.time.LocalDate
import kotlin.collections.filter

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

    suspend fun getUserPerformances(userId: String): List<PerformanceResponseDto> {
        val response = api.getUserPerformances(userId)
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Failed to load performances")
        }
    }

    suspend fun getPerformancesByDate(userId: String, date: LocalDate): List<PerformanceRecord> {
        val allPerformances = getUserPerformances(userId)

        val dateString = date.toString()  // "2025-07-09"

        return allPerformances
            .filter { it.attendingDate.take(10) == dateString }
            .map { dto ->
                PerformanceRecord(
                    title = dto.name,
                    date = dto.attendingDate.take(10),
                    thumbnailURL = dto.photoUrl
                )
            }
    }

}

data class PerformanceRecord(
    val title: String,
    val date: String,
    val thumbnailURL: String
)
