package com.example.kolejka.use_cases.news

import com.example.kolejka.data.features.news.repository.NewsRepository

class GetNewsByIdUseCase(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(newsId: String) = repository.getNewsById(newsId)
}