package com.example.kolejka.view.ui.screens.notification_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.paging.compose.items
import com.example.kolejka.models.notification.Notification
import com.example.kolejka.models.notification.NotificationAction
import com.example.kolejka.view.theme.DarkPurple
import com.example.kolejka.view.theme.PaddingMedium
import com.example.kolejka.view.ui.components.notification.NotificationComposable
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import kotlin.random.Random


@Composable
fun NotificationScreen(
    viewModel: NotificationScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val notifications = viewModel.notifications.collectAsLazyPagingItems()

    val isRefreshing by viewModel.isRefreshing.collectAsState()

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = { viewModel.refreshScreen(notifications) }
    ){
        Scaffold(scaffoldState = scaffoldState) {
            Box {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(PaddingMedium)
                ) {

                    items(notifications) { notification ->
                        NotificationComposable(
                            notification = notification?.toNotification(),
                            navController
                        )
                    }
                }

                notifications.apply {
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