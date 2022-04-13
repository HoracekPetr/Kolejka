package com.example.kolejka.view.ui.screens.news_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.kolejka.view.theme.DarkPurple
import com.example.kolejka.view.ui.components.news.NewsComposable
import com.example.kolejka.view.util.Screen
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NewsScreen(
    viewModel: NewsScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val news = viewModel.news.collectAsLazyPagingItems()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = { viewModel.refreshScreen(news) }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(news) { newsItem ->
                    NewsComposable(news = newsItem?.toNews()){
                        navController.navigate(Screen.NewsDetailScreen.route + "?newsId=${newsItem?.id}")
                    }
                }
            }
        }
    }
}
