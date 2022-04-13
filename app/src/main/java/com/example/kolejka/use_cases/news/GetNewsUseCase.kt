package com.example.kolejka.use_cases.news

import com.example.kolejka.data.features.news.repository.NewsRepository

class GetNewsUseCase(
    private val repository: NewsRepository
) {
   operator fun invoke() = repository.news
}