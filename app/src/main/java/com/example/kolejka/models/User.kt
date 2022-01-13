package com.example.kolejka.models

data class User(
        val userId: String? = "",
        var username: String,
        val profilePictureUrl: String = "",
        var bannerR: Float = 255f,
        var bannerG: Float = 255f,
        var bannerB: Float = 255f,
)
