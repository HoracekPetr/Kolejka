package com.example.kolejka.data.features.post.dto.request

data class CreatePostRequest(
    val title: String,
    val description: String,
    val limit: Int,
    val type: Int,
    val date: String,
    val location: String,
)