package com.example.kolejka.view.ui.screens.edit_profile_dialog

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kolejka.R
import com.example.kolejka.models.User
import com.example.kolejka.view.theme.*
import com.example.kolejka.view.ui.components.profile.edit_profile_dialog.EditProfileDialogViewModel
import com.example.kolejka.view.ui.components.textfields.EditUsernameTextField

@Composable
fun EditProfileDialog(
    user: User,
    onConfirmRequestClick: () -> Unit = {},
    onDismissRequestClick: () -> Unit = {},
    onProfilePictureClick: () -> Unit = {},
    viewModel: EditProfileDialogViewModel = hiltViewModel()
) {
    val userColor = Color(user.bannerColorR/255, user.bannerColorG/255, user.bannerColorB/255)
    Log.d("userColor", userColor.toString())
    viewModel.setR(user.bannerColorR)
    viewModel.setG(user.bannerColorG)
    viewModel.setB(user.bannerColorB)

    Dialog(onDismissRequest = { onDismissRequestClick() }, content = {
        Card(
            elevation = Space8,
            shape = RoundedCornerShape(Space12)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaddingMedium)
                    .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Image(
                    modifier = Modifier
                        .size(ProfilePicSize)
                        .clip(RoundedCornerShape(Space36))
                        .clickable { onProfilePictureClick() },
                    painter = painterResource(id = R.drawable.petr),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(Space8))
                EditUsernameTextField(
                    text = viewModel.username.value,
                    hint = user.username,
                    onTextChanged = { viewModel.setUsername(it) })

                Slider(
                    value = viewModel.r.value,
                    onValueChange = { viewModel.setR(it)},
                    valueRange = 0f..255f,
                    onValueChangeFinished = {
                        user.bannerColorR = viewModel.r.value
                    }
                )

                Spacer(modifier = Modifier.size(Space4))

                Slider(
                    value = viewModel.g.value,
                    onValueChange = { viewModel.setG(it)},
                    valueRange = 0f..255f,
                    onValueChangeFinished = {
                        user.bannerColorG = viewModel.g.value
                    }
                )

                Spacer(modifier = Modifier.size(Space4))

                Slider(
                    value = viewModel.b.value,
                    onValueChange = { viewModel.setB(it)},
                    valueRange = 0f..255f,
                    onValueChangeFinished = {
                        user.bannerColorB = viewModel.b.value
                    }
                )

                Canvas(modifier = Modifier.size(ProfilePicSize), onDraw = {
                    drawCircle(
                        color = BlackAccent,
                        style = Stroke(width = 5f)
                    )
                    drawCircle(
                        color = Color(
                            user.bannerColorR/255,
                            user.bannerColorG/255,
                            user.bannerColorB/255
                        )
                    )
                })

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = {
                        onConfirmRequestClick()
                        if(viewModel.username.value != "") {
                            user.username = viewModel.username.value
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null)
                    }

                    Button(onClick = {
                        onDismissRequestClick()
                        user.bannerColorR = userColor.red*255
                        user.bannerColorG = userColor.green*255
                        user.bannerColorB = userColor.blue*255
                    }) {
                        Icon(imageVector = Icons.Default.Cancel, contentDescription = null)
                    }

                }

            }
        }
    })
}