package com.example.kolejka.view.ui.components.posts

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.example.kolejka.models.Post
import com.example.kolejka.view.util.PostType
import com.example.kolejka.view.util.Screen

@Composable
fun PostList(posts: LazyPagingItems<Post>, navController: NavController, screenType: PostType) {
    LazyColumn {
        when(screenType){
            PostType.Event -> {
                items(posts) { post ->
                    if (post != null && post.type == PostType.Event.type ) {
                        PostComposable(post = post) {
                            navController.navigate(Screen.PostDetailScreen.route + "?postId=${post.id}")
                        }
                    }
                }
            }
            PostType.Offer -> {
                items(posts) { post ->
                    if (post != null && post.type == PostType.Offer.type ) {
                        PostComposable(post = post) {
                            navController.navigate(Screen.PostDetailScreen.route + "?postId=${post.id}")
                        }
                    }
                }
            }
            else -> {}
        }
    }
}