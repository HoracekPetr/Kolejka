package com.example.kolejka.view.ui.screens.profile_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColor
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.kolejka.models.User
import com.example.kolejka.view.theme.PaddingMedium
import com.example.kolejka.view.theme.ProfilePicSize
import com.example.kolejka.view.ui.components.StandardTextField
import com.example.kolejka.view.ui.components.profile.ProfileBannerComposable
import com.example.kolejka.view.ui.screens.edit_profile_dialog.EditProfileDialog
import com.example.kolejka.view.util.Screen
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor

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
        }
    }
}



