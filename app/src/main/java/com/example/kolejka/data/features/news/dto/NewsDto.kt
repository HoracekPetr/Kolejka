package com.example.kolejka.data.features.news.dto

import com.example.kolejka.models.News
import java.text.SimpleDateFormat
import java.util.*

data class NewsDto(
    val id: String,
    val title: String,
    val description: String,
    val timestamp: Long
) {
    fun toNews(): News {
        return News(
            id = id,
            title = title,
            description = description,
            formattedTime = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).run {
                format(timestamp)
            }
        )
    }
}
