package com.example.kolejka.data.features.post.dto.request

data class NewPostRequest(
    val title: String,
    val description: String,
    val limit: Int,
    val type: Int,
    val date: String,
    val location: String,
    val price: Int? = null,
    val postImageURL: String
)
