package com.example.kolejka.view.ui.screens.profile_screen.other_user_screen

import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import com.example.kolejka.models.Post
import com.example.kolejka.models.User
import kotlinx.coroutines.flow.Flow

data class OtherProfileState(
    val profile: User? = null,
    val posts: Flow<PagingData<Post>>? = null,
    val isLoading: Boolean = false
)
