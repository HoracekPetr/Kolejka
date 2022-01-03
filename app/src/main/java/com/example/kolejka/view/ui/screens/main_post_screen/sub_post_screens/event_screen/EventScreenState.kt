package com.example.kolejka.view.ui.screens.main_post_screen.sub_post_screens.event_screen

import com.example.kolejka.models.Post

data class EventScreenState(
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val page: Int = 0
)
