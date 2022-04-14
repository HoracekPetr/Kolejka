package com.example.kolejka.use_cases.news

import com.example.kolejka.data.features.news.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNewsUseCase(
    private val repository: NewsRepository
) {

   operator fun invoke() = repository.news
}