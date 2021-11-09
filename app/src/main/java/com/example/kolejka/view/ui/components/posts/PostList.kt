package com.example.kolejka.view.ui.components.posts

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable

@Composable
fun PostList(posts: List<Post>) {
    LazyColumn{
        items(posts){ post ->
            PostComposable(post = post)
        }
    }
}