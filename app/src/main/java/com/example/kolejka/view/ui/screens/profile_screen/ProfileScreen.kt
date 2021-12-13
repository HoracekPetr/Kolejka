package com.example.kolejka.view.ui.screens.profile_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.kolejka.R
import com.example.kolejka.models.Post
import com.example.kolejka.models.User
import com.example.kolejka.view.theme.*
import com.example.kolejka.view.ui.components.posts.PostStrip
import com.example.kolejka.view.ui.components.profile.ProfileBannerComposable
import com.example.kolejka.view.ui.screens.edit_profile_dialog.EditProfileDialog
import com.example.kolejka.view.util.PostType

val posts = listOf(
    Post("Prdel", description = "huehue", type = PostType.Event.type),
    Post("Kozy", description = "huehue", type = PostType.Offer.type),
    Post("Piča", description = "huehue", type = PostType.Offer.type),
    Post("Kurva", description = "huehue", type = PostType.Event.type),
    Post("Hovno", description = "huehue", type = PostType.Event.type),
    Post("Hajzel", description = "huehue", type = PostType.Offer.type),
    Post("Hajzelasdasdasdasd", description = "huehue", type = PostType.Event.type),
)

@ExperimentalGraphicsApi
@Composable
fun ProfileScreen(
    navController: NavController,
    user: User,
    viewModel: ProfileScreenViewModel = hiltViewModel()
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(PaddingMedium)
    ) {
        item {
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
            Spacer(modifier = Modifier.size(Space4))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RadioButton(
                        colors = RadioButtonDefaults.colors(selectedColor = DarkPurple, unselectedColor = DarkGray),
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
                        colors = RadioButtonDefaults.colors(selectedColor = DarkPurple, unselectedColor = DarkGray),
                        selected = viewModel.offersRadioEnabled.value,
                        onClick = { viewModel.setJoinedPostsRadioEnabled(true) })
                    Text(
                        stringResource(R.string.joined_posts),
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            }
        }

        items(
            when {
                viewModel.eventsRadioEnabled.value -> {
                    posts.filter { it.type == PostType.Event.type }
                }
                viewModel.offersRadioEnabled.value -> {
                    posts.filter { it.type == PostType.Offer.type }
                }
                else -> {
                    posts
                }
            }
        ) { post ->
            PostStrip(
                post = post,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaddingMedium)
                    .clickable { })
        }
    }
}



