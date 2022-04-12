package com.example.kolejka.data.features.post.dto.request

data class UpdatePostRequest(
    val postId: String,
    val title: String,
    val description: String,
    val limit: Int?,
    val date: String,
    val location: String,
    val postPictureUrl: String?
)