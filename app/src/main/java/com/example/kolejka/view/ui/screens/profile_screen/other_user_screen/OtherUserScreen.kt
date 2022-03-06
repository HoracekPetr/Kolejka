package com.example.kolejka.view.ui.screens.profile_screen.other_user_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.kolejka.R
import com.example.kolejka.view.theme.DarkGray
import com.example.kolejka.view.theme.DarkPurple
import com.example.kolejka.view.theme.PaddingMedium
import com.example.kolejka.view.theme.Space4
import com.example.kolejka.view.ui.components.posts.PostStrip
import com.example.kolejka.view.ui.components.profile.LogoutDialog
import com.example.kolejka.view.ui.components.profile.ProfileBannerComposable
import com.example.kolejka.view.ui.components.profile.edit_profile_dialog.EditProfileDialog
import com.example.kolejka.view.ui.screens.profile_screen.ProfileScreenViewModel
import com.example.kolejka.view.util.Screen
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun OtherUserScreen(
    navController: NavController,
    viewModel: OtherUserScreenViewModel = hiltViewModel(),
    userId: String? = null
) {

    val postsByOtherCreator = viewModel.state.value.posts?.collectAsLazyPagingItems()

    val state = viewModel.state.value
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = { viewModel.refreshScreen(userId ?: "") }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(PaddingMedium)
        ) {
            item {
                state.profile?.let { user ->
                    ProfileBannerComposable(
                        user = user,
                        isLoggedUser = false
                    )
                }
                Spacer(modifier = Modifier.size(Space4))
            }

            if(postsByOtherCreator != null){
                items(postsByOtherCreator) { post ->

                    post?.let {
                        PostStrip(
                            post = it,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(PaddingMedium)
                                .clickable {
                                    navController.navigate(Screen.PostDetailScreen.route + "?postId=${it.id}")
                                }
                        )
                    }

                    postsByOtherCreator.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> {
                                CircularProgressIndicator(
                                    color = DarkPurple
                                )
                            }
                            loadState.append is LoadState.Loading -> {
                                CircularProgressIndicator(
                                    color = DarkPurple
                                )
                            }
                            loadState.append is LoadState.Error -> {}
                        }
                    }
                }
            }
        }
    }
}
