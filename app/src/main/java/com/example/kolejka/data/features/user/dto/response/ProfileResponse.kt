package com.example.data.responses

import com.example.kolejka.models.User

data class ProfileResponse(
    val username: String,
    val profilePictureUrl: String,
    val bannerR: Float,
    val bannerG: Float,
    val bannerB: Float
)
