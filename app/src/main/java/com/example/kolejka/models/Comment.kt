package com.example.kolejka.models

data class Comment(
        val id: String = "",
        val postId: String = "",
        val username: String = "",
        val profilePictureUrl: String = "",
        val comment: String = "",
        val timestamp: Long = 0L,
        val userId: String = "",
)
