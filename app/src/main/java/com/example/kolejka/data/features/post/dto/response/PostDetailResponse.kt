package com.example.kolejka.data.features.post.dto.response

import com.example.kolejka.models.Post

data class PostDetailResponse(
    val post: Post?,
    val requesterId: String
)
