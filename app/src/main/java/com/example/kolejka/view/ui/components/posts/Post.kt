package com.example.kolejka.view.ui.components.posts

data class Post(
    val title: String,
    val description: String,
    val postImageUrl: String = "",
    val postProfilePictureUrl: String = ""
)
