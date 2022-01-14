package com.example.kolejka.view.ui.components.profile.edit_profile_dialog

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.kolejka.R
import com.example.kolejka.view.theme.*
import com.example.kolejka.view.ui.components.StandardTextField
import com.example.kolejka.view.ui.screens.new_post_screen.NewPostEvent
import com.example.kolejka.view.util.UiEvent
import com.example.kolejka.view.util.uitext.asString
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EditProfileDialog(
    //user: User,
    onConfirmRequestClick: () -> Unit = {},
    onDismissRequestClick: () -> Unit = {},
    viewModel: EditProfileDialogViewModel = hiltViewModel()
) {

    val editProfileState = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val localContext = LocalContext.current

    val getImageFromGallery =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            viewModel.onEvent(EditProfileEvent.PickedImage(it))
        }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(localContext),
                        duration = SnackbarDuration.Short
                    )
                }
                else -> {
                }
            }
        }
    }


    //val userColor = Color(editProfileState.bannerR/255, editProfileState.bannerG/255, editProfileState.bannerB/255)

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
                        .clickable {
                            getImageFromGallery.launch("image/*")
                        },
                    painter = if (viewModel.profileImageUri.value == null) {
                        rememberImagePainter(data = editProfileState.profileImageUrl)
                    } else {
                        rememberImagePainter(data = viewModel.profileImageUri)
                    },
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(Space8))

                StandardTextField(
                    modifier = Modifier.border(
                        1.dp,
                        Color.DarkGray,
                        shape = RoundedCornerShape(10.dp)
                    ),
                    text = editProfileState.username,
                    hint = stringResource(R.string.username),
                    onTextChanged = { viewModel.onEvent(EditProfileEvent.EnteredUsername(it)) },
                    placeholderTextColor = DarkGray,
                    textStyle = MaterialTheme.typography.body1,
                    placeholderTextStyle = MaterialTheme.typography.body1,
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Person, contentDescription = null)
                    },
                    textfieldColors = TextFieldDefaults.textFieldColors(backgroundColor = LightBackgroundWhite)
                )

                Slider(
                    value = editProfileState.bannerR,
                    onValueChange = { viewModel.onEvent(EditProfileEvent.ChangedR(it)) },
                    valueRange = 0f..255f,
                    onValueChangeFinished = {}
                )

                Spacer(modifier = Modifier.size(Space4))

                Slider(
                    value = editProfileState.bannerG,
                    onValueChange = { viewModel.onEvent(EditProfileEvent.ChangedG(it)) },
                    valueRange = 0f..255f,
                    onValueChangeFinished = {}
                )

                Spacer(modifier = Modifier.size(Space4))

                Slider(
                    value = editProfileState.bannerB,
                    onValueChange = { viewModel.onEvent(EditProfileEvent.ChangedB(it)) },
                    valueRange = 0f..255f,
                    onValueChangeFinished = {}
                )

                Canvas(modifier = Modifier.size(ProfilePicSize), onDraw = {
                    drawCircle(
                        color = BlackAccent,
                        style = Stroke(width = 5f)
                    )
                    drawCircle(
                        color = Color(
                            editProfileState.bannerR / 255,
                            editProfileState.bannerG / 255,
                            editProfileState.bannerB / 255,
                        )
                    )
                })

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = {
                        onConfirmRequestClick()
                        viewModel.onEvent(EditProfileEvent.UpdateProfile)
                    }) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null)
                    }

                    Button(onClick = {
                        onDismissRequestClick()
                    }) {
                        Icon(imageVector = Icons.Default.Cancel, contentDescription = null)
                    }

                }

            }
        }
    })
}
