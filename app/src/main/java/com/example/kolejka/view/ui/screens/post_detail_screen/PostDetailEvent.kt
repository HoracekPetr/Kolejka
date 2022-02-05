package com.example.kolejka.view.ui.screens.post_detail_screen

sealed class PostDetailEvent{
    object Comment: PostDetailEvent()
    object AddMember: PostDetailEvent()
}
