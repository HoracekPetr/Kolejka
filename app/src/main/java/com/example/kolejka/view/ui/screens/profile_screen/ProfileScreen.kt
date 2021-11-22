package com.example.kolejka.view.ui.screens.profile_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColor
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.kolejka.R
import com.example.kolejka.models.Post
import com.example.kolejka.models.User
import com.example.kolejka.view.theme.*
import com.example.kolejka.view.ui.components.StandardTextField
import com.example.kolejka.view.ui.components.posts.PostStrip
import com.example.kolejka.view.ui.components.profile.ProfileBannerComposable
import com.example.kolejka.view.ui.screens.edit_profile_dialog.EditProfileDialog
import com.example.kolejka.view.util.Screen
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor

val posts = listOf(
    Post("Prdel", description = "huehue", isEvent = true),
    Post("Kozy", description = "huehue", isOffer = true),
    Post("Piča", description = "huehue", isEvent = true),
    Post("Kurva", description = "huehue", isEvent = true),
    Post("Hovno", description = "huehue", isOffer = true),
    Post("Hajzel", description = "huehue", isEvent = true),
    Post("Hajzelasdasdasdasd", description = "huehue", isOffer = true),
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
                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    RadioButton(selected = viewModel.yourPostsRadioEnabled.value, onClick = { viewModel.setYourPostsRadioEnabled(true) })
                    Text(text = stringResource(R.string.events), style = MaterialTheme.typography.subtitle2)
                }
                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    RadioButton(selected = viewModel.joinedPostsRadioEnabled.value, onClick = { viewModel.setJoinedPostsRadioEnabled(true) })
                    Text(stringResource(R.string.offers), style = MaterialTheme.typography.subtitle2)
                }
            }
        }

        items(
            when {
                viewModel.yourPostsRadioEnabled.value -> {
                    posts.filter{it.isEvent}
                }
                viewModel.joinedPostsRadioEnabled.value -> {
                    posts.filter{it.isOffer}
                }
                else -> {
                    posts
                }
            }
        ){ post ->
            PostStrip(post = post, modifier = Modifier.fillMaxWidth().padding(PaddingExtraLarge).clickable {  })
        }
    }
}



