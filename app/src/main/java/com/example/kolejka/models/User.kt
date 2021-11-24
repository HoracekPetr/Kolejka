package com.example.kolejka.models

data class User(
        val userId: String = "",
        var username: String,
        val profilePictureUrl: String = "",
        var bannerColorR: Float = 125f,
        var bannerColorG: Float = 100f,
        var bannerColorB: Float = 125f,
)
