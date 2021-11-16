package com.example.kolejka.view.ui.components.posts

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.kolejka.models.Post
import com.example.kolejka.view.util.Screen

@Composable
fun PostList(posts: List<Post>, navController: NavController) {
    LazyColumn{
        items(posts){ post ->
            PostComposable(post = post){
                navController.navigate(Screen.PostDetailScreen.route)
            }
        }
    }
}