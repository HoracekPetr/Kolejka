package com.example.kolejka.models

import javax.inject.Inject

data class Post(
        val title: String,
        val id: String = "",
        val userId: String = "",
        val username: String = "",
        val description: String,
        val postImageUrl: String = "",
        val members: List<String> = emptyList<String>(),
        var available: Int = 0,
        val limit: Int = 0,
        val isOffer: Boolean = false,
        val isEvent: Boolean = false,
)
