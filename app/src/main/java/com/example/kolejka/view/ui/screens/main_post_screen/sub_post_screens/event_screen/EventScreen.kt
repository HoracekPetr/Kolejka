package com.example.kolejka.view.ui.screens.main_post_screen.sub_post_screens.event_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.kolejka.models.Post
import com.example.kolejka.view.theme.DarkPurple
import com.example.kolejka.view.ui.components.posts.PostComposable
import com.example.kolejka.view.ui.components.posts.PostList
import com.example.kolejka.view.util.PostType
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch


@Composable
fun EventScreen(navController: NavController, viewModel: EventScreenViewModel = hiltViewModel()) {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val isRefreshing by viewModel.isRefreshing.collectAsState()

    val posts = viewModel.posts.collectAsLazyPagingItems()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = { viewModel.refreshScreen(posts) }
    ) {
        Scaffold(
            scaffoldState = scaffoldState
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                PostList(posts = posts, navController = navController, screenType = PostType.Event)

                posts.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center),
                                color = DarkPurple
                            )
                        }
                        loadState.append is LoadState.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center),
                                color = DarkPurple
                            )
                        }
                        loadState.append is LoadState.Error -> {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "An error has occured."
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


