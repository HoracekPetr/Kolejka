package com.example.kolejka.data.features.news.repository

import androidx.paging.PagingData
import com.example.kolejka.data.features.news.dto.NewsDto
import com.example.kolejka.data.util.Resource
import com.example.kolejka.models.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    val news: Flow<PagingData<NewsDto>>

    suspend fun getNewsById(newsId: String): Resource<NewsDto>

}