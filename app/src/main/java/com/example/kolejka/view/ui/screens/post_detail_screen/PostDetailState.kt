package com.example.kolejka.view.ui.screens.post_detail_screen

import com.example.kolejka.models.Comment
import com.example.kolejka.models.Post

data class PostDetailState(
    val isLoading: Boolean = false,
    val requesterId: String? = "",
    val post: Post? = null,
    val comments: List<Comment>? = null,
    val showDeletePostDialog: Boolean = false,
    val showDeleteCommentDialog: Boolean = false,
)
