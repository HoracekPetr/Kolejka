package com.example.kolejka.view.ui.screens.main_post_screen.sub_post_screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.kolejka.R
import com.example.kolejka.models.Post
import com.example.kolejka.view.theme.DarkPurple
import com.example.kolejka.view.ui.components.posts.PostList
import com.example.kolejka.view.ui.screens.main_post_screen.sub_post_screens.event_screen.EventScreenViewModel
import com.example.kolejka.view.util.PostType
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch


@Composable
fun EventScreen(navController: NavController, viewModel: EventScreenViewModel = hiltViewModel()) {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val posts = viewModel.posts.collectAsLazyPagingItems()

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