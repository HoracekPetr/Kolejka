package com.example.kolejka.view.ui.screens.notification_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.kolejka.view.theme.*
import com.example.kolejka.view.ui.components.notification.NotificationComposable
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch


@Composable
fun NotificationScreen(
    viewModel: NotificationScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val notifications = viewModel.notifications.collectAsLazyPagingItems()

    var clickedDeleteNotification by remember { mutableStateOf(false)}

    val isRefreshing by viewModel.isRefreshing.collectAsState()

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = { viewModel.refreshScreen(notifications) }
    ) {
        Scaffold(scaffoldState = scaffoldState, floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    if(clickedDeleteNotification){
                        Text(text = "Delete notifications?", style = MaterialTheme.typography.subtitle2)
                    }
                },
                onClick = {
                    clickedDeleteNotification = !clickedDeleteNotification
                },
                icon = {
                    Icon(imageVector = Icons.Default.DeleteSweep, contentDescription = "Delete notifications")
                },
                backgroundColor = MediumOpaquePurple,
                elevation = FloatingActionButtonDefaults.elevation(Space8)
            )
        }) {
            Box {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = PaddingMedium, top = PaddingMedium, end = PaddingMedium)
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