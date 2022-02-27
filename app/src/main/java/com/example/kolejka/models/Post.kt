package com.example.kolejka.models

data class Post(
        val title: String,
        val id: String = "",
        val userId: String = "",
        val username: String = "",
        val description: String,
        val postPictureUrl: String = "",
        val members: List<String> = mutableListOf(),
        var available: Int = 0,
        val limit: Int? = 0,
        val type: Int = 0,
        val profilePictureUrl: String? = "",
        val date: String? = null,
        val location: String? = null
)
