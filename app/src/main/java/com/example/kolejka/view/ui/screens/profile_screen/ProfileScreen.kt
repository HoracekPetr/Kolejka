package com.example.kolejka.view.ui.screens.profile_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.kolejka.R
import com.example.kolejka.view.theme.*
import com.example.kolejka.view.ui.components.posts.PostStrip
import com.example.kolejka.view.ui.components.profile.ProfileBannerComposable
import com.example.kolejka.view.ui.screens.edit_profile_dialog.EditProfileDialog

@ExperimentalGraphicsApi
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileScreenViewModel = hiltViewModel(),
) {

    val state = viewModel.state.value
    val postsByCreator = viewModel.postsByCreator.collectAsLazyPagingItems()
    val postsWhereMember = viewModel.postsWhereMember.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(PaddingMedium)
    ) {
        item {
            state.profile?.let { user ->
                ProfileBannerComposable(
                    user = user,
                    onEditIconClick = {
                        viewModel.setEditProfileDialogEnabled(true)
                    }
                )

                if (viewModel.showEditProfileDialog.value) {
                    EditProfileDialog(
                        user = user,
                        onDismissRequestClick = { viewModel.setEditProfileDialogEnabled(false) },
                        onConfirmRequestClick = { viewModel.setEditProfileDialogEnabled(false) })
                }
            }
            Spacer(modifier = Modifier.size(Space4))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RadioButton(
                        colors = RadioButtonDefaults.colors(
                            selectedColor = DarkPurple,
                            unselectedColor = DarkGray
                        ),
                        selected = viewModel.eventsRadioEnabled.value,
                        onClick = { viewModel.setYourPostsRadioEnabled(true) })
                    Text(
                        text = stringResource(R.string.my_posts),
                        style = MaterialTheme.typography.subtitle2
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RadioButton(
                        colors = RadioButtonDefaults.colors(
                            selectedColor = DarkPurple,
                            unselectedColor = DarkGray
                        ),
                        selected = viewModel.offersRadioEnabled.value,
                        onClick = { viewModel.setJoinedPostsRadioEnabled(true) })
                    Text(
                        stringResource(R.string.joined_posts),
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            }
        }

        if(viewModel.eventsRadioEnabled.value){
            items(postsByCreator) { post ->

                post?.let {
                    PostStrip(
                        post = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(PaddingMedium)
                            .clickable { }
                    )
                }

                postsByCreator.apply {
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

        if(viewModel.offersRadioEnabled.value){
            items(postsWhereMember) { post ->

                post?.let {
                    PostStrip(
                        post = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(PaddingMedium)
                            .clickable { }
                    )
                }

                postsByCreator.apply {
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





