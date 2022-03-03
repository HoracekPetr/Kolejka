package com.example.kolejka.data.features.user.dto.request

data class UpdateUserRequest(
    val username: String,
    val bannerR: Float,
    val bannerG: Float,
    val bannerB: Float,
    val profilePictureURL: String?
)
