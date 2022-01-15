package com.example.kolejka.view.ui.screens.new_post_screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.kolejka.R
import com.example.kolejka.view.theme.*
import com.example.kolejka.view.ui.components.StandardTextField
import com.example.kolejka.view.ui.components.bottom_navigation.FloatingAddPostButton
import com.example.kolejka.view.util.Screen
import com.example.kolejka.view.util.UiEvent
import com.example.kolejka.view.util.crop.CropActivityResultContract
import com.example.kolejka.view.util.uitext.asString
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NewPostScreen(
    navController: NavController,
    viewModel: NewPostScreenViewModel = hiltViewModel()
) {
    val localFocusManager = LocalFocusManager.current
    val localContext = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    val scaffoldState = rememberScaffoldState()

    val optionsRadio = viewModel.optionsRadioState

    val imageUri = viewModel.pickedImageUri

    val title = viewModel.titleState
    val description = viewModel.descriptionState
    val limit = viewModel.limitState


    val cropActivityLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(aspectX = 16f, aspectY = 9f)
    ) {
        viewModel.onEvent(NewPostEvent.CropImage(it))
    }

    val getImageFromGallery =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            cropActivityLauncher.launch(it)
        }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.popBackStack()
                    navController.navigate(event.route)
                }
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(localContext),
                        duration = SnackbarDuration.Short,

                        )
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { localFocusManager.clearFocus() }
                .padding(PaddingMedium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Add Post Picture
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.35f)
                    .clip(
                        RoundedCornerShape(
                            Space4
                        )
                    )
                    .border(2.dp, BlackAccent)
                    .background(Color.White)
                    .clickable {
                        getImageFromGallery.launch("image/*")
                    }
            ) {
                Icon(
                    modifier = Modifier.align(Alignment.Center),
                    imageVector = Icons.Default.AddAPhoto,
                    contentDescription = stringResource(R.string.add_a_new_photo)
                )

                imageUri.value?.let { uri ->
                    Image(
                        modifier = Modifier.matchParentSize(),
                        painter = rememberImagePainter(
                            request = ImageRequest.Builder(LocalContext.current)
                                .data(uri)
                                .build()
                        ),
                        contentDescription = "Picked image for the post"
                    )
                }
            }
            //Choose Post type
            Spacer(modifier = Modifier.size(Space4))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RadioButton(
                        selected = optionsRadio.value.eventEnabled,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = DarkPurple,
                            unselectedColor = DarkGray
                        ),
                        onClick = { viewModel.onEvent(NewPostEvent.EventPicked) })
                    Text(
                        text = stringResource(R.string.event),
                        style = MaterialTheme.typography.subtitle2
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RadioButton(
                        selected = optionsRadio.value.offerEnabled,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = DarkPurple,
                            unselectedColor = DarkGray
                        ),
                        onClick = { viewModel.onEvent(NewPostEvent.OfferPicked) })
                    Text(stringResource(R.string.offer), style = MaterialTheme.typography.subtitle2)
                }
            }
            Spacer(modifier = Modifier.size(Space12))
            StandardTextField(
                modifier = Modifier.fillMaxWidth(),
                text = title.value.text,
                hint = stringResource(R.string.title),
                textStyle = MaterialTheme.typography.body1,
                onTextChanged = { viewModel.onEvent(NewPostEvent.EnteredTitle(it)) },
                placeholderTextColor = DarkGray,
                placeholderTextStyle = MaterialTheme.typography.body1,
            )
            Spacer(modifier = Modifier.size(Space8))
            StandardTextField(
                modifier = Modifier
                    .fillMaxHeight(0.4f)
                    .fillMaxWidth(),
                text = description.value.text,
                hint = stringResource(id = R.string.description),
                onTextChanged = { viewModel.onEvent(NewPostEvent.EnteredDescription(it)) },
                placeholderTextColor = DarkGray,
                textStyle = MaterialTheme.typography.h3,
                placeholderTextStyle = MaterialTheme.typography.h3,
                singleLine = false,
                maxLines = 3
            )
            Spacer(modifier = Modifier.size(Space8))
            StandardTextField(
                modifier = Modifier.fillMaxWidth(0.25f),
                text = limit.value.text,
                hint = stringResource(id = R.string.limit),
                onTextChanged = { viewModel.onEvent(NewPostEvent.EnteredLimit(it)) },
                placeholderTextColor = DarkGray,
                textStyle = TextStyle(
                    fontFamily = roboto_mono,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                ),
                placeholderTextAlignment = TextAlign.Justify,
                placeholderTextStyle = MaterialTheme.typography.h3,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.size(Space8))
            if (viewModel.isLoading.value) {

                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = DarkPurple
                )

            } else {
                FloatingAddPostButton(
                    showButton = true,
                    buttonIcon = Icons.Default.Create,
                    buttonText = stringResource(
                        id = R.string.create_the_post
                    ),
                    iconDescription = ""
                ) {
                    viewModel.onEvent(NewPostEvent.CreatePost)
                }
            }
        }
    }
}