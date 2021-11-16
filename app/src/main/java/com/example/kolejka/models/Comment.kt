package com.example.kolejka.models

data class Comment(
        val commentId: Int = -1,
        val username: String = "",
        val profilePictureUrl: String = "",
        val comment: String = ""
)
