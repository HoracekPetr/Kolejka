package com.example.kolejka.view.ui.screens.notification_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.kolejka.R
import com.example.kolejka.view.theme.*
import com.example.kolejka.view.ui.components.notification.NotificationComposable
import com.example.kolejka.view.util.UiEvent
import com.example.kolejka.view.util.uitext.asString
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Composable
fun NotificationScreen(
    viewModel: NotificationScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val notifications = viewModel.notifications.collectAsLazyPagingItems()

    val isRefreshing by viewModel.isRefreshing.collectAsState()

    val localContext = LocalContext.current

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(localContext),
                        duration = SnackbarDuration.Short,
                    )
                }
                else -> {}
            }
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = { viewModel.refreshScreen(notifications) }
    ) {
        Scaffold(scaffoldState = scaffoldState, floatingActionButton = {
            if (viewModel.clickedDeleteNotifications.value) {
                ExtendedFloatingActionButton(
                    text = {
                        Text(
                            text = "Delete notifications?",
                            style = MaterialTheme.typography.subtitle2
                        )

                        IconButton(onClick = {
                            viewModel.onEvent(NotificationScreenEvent.DeleteNotification(notifications))
                            viewModel.onEvent(NotificationScreenEvent.ExpandDeleteNotification)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = stringResource(R.string.delete),
                                tint = MediumGreen
                            )
                        }
                        IconButton(onClick = {
                            viewModel.onEvent(NotificationScreenEvent.ExpandDeleteNotification)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.close),
                                tint = MediumRed
                            )
                        }
                    },
                    onClick = {
                        viewModel.onEvent(NotificationScreenEvent.ExpandDeleteNotification)
                    },
                    icon = {

                    },
                    backgroundColor = MediumOpaquePurple,
                    elevation = FloatingActionButtonDefaults.elevation(Space8)
                )
            } else {
                FloatingActionButton(
                    onClick = {
                        viewModel.onEvent(NotificationScreenEvent.ExpandDeleteNotification)
                    },
                    backgroundColor = MediumOpaquePurple,
                    elevation = FloatingActionButtonDefaults.elevation(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete notifications"
                    )
                }
            }
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
