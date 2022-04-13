package com.example.kolejka.view.ui.screens.edit_post_screen

import android.util.Log
import android.view.ContextThemeWrapper
import android.widget.CalendarView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.R
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.kolejka.models.Post
import com.example.kolejka.models.User
import com.example.kolejka.view.theme.*
import com.example.kolejka.view.ui.components.StandardTextField
import com.example.kolejka.view.ui.components.bottom_navigation.FloatingAddPostButton
import com.example.kolejka.view.ui.screens.new_post_screen.NewPostEvent
import com.example.kolejka.view.util.Constants
import com.example.kolejka.view.util.PostType
import com.example.kolejka.view.util.UiEvent
import com.example.kolejka.view.util.crop.CropActivityResultContract
import com.example.kolejka.view.util.uitext.asString
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EditPostScreen(
    navController: NavController,
    postId: String? = null,
    viewModel: EditPostScreenViewModel = hiltViewModel()
) {
    val localFocusManager = LocalFocusManager.current
    val localContext = LocalContext.current
    val viewRequester = BringIntoViewRequester()

    val coroutineScope = rememberCoroutineScope()

    val interactionSource = remember { MutableInteractionSource() }
    val scaffoldState = rememberScaffoldState()

    val scrollState = rememberScrollState()

    val imageUri = viewModel.pickedImageUri

    val type = viewModel.postType.value
    val title = viewModel.titleState.value
    val description = viewModel.descriptionState.value
    val location = viewModel.locationState.value
    var date = viewModel.selectedDate
    val limit = viewModel.limitState.value
    val url = viewModel.imageUrl


    val cropActivityLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(aspectX = 16f, aspectY = 9f)
    ) {
        Log.d("URI IS", "$it")
        viewModel.onEvent(EditPostEvent.CropImage(it))
    }

    val getImageFromGallery =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            if (it != null) {
                Log.d("URI IS", "$it")
                cropActivityLauncher.launch(it)
            }
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
                is UiEvent.Navigate -> {
                    navController.popBackStack()
                    navController.navigate(event.route)
                }

                else -> {}
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
                .verticalScroll(scrollState)
                .padding(PaddingMedium),
            //horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Post Picture

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    //.fillMaxHeight(0.35f)
                    .clip(
                        RoundedCornerShape(
                            Space4
                        )
                    )
                    .border(2.dp, BlackAccent)
                    .background(Color.White)
                    .clickable {
                        getImageFromGallery.launch("image/*")
                    },
                painter = rememberImagePainter(
                    request = ImageRequest.Builder(LocalContext.current)
                        .data(if (viewModel.pickedImageUri.value == null) url.value else viewModel.pickedImageUri.value)
                        .build()
                ),
                contentDescription = null
            )

            Spacer(modifier = Modifier.size(Space12))


            //TITLE

            StandardTextField(
                modifier = Modifier.fillMaxWidth(),
                text = title.text,
                hint = stringResource(com.example.kolejka.R.string.title),
                textStyle = MaterialTheme.typography.body1,
                onTextChanged = { viewModel.onEvent(EditPostEvent.EnteredTitle(it)) },
                placeholderTextColor = DarkGray,
                placeholderTextStyle = MaterialTheme.typography.body1,
            )
            Spacer(modifier = Modifier.size(Space24))

            //DESCRIPTION

            StandardTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusEvent {
                        if (it.isFocused) {
                            coroutineScope.launch {
                                delay(200)
                                viewRequester.bringIntoView()
                            }
                        }
                    }
                    .bringIntoViewRequester(viewRequester)
                    .height(125.dp),
                text = description.text,
                hint = stringResource(id = com.example.kolejka.R.string.description),
                onTextChanged = {
                    if (it.length <= Constants.DESC_MAX_CHARS) {
                        viewModel.onEvent(
                            EditPostEvent.EnteredDescription(it)
                        )
                    }
                },

                placeholderTextColor = DarkGray,
                textStyle = MaterialTheme.typography.h3,
                placeholderTextStyle = MaterialTheme.typography.h3,
                singleLine = false,
                maxLines = 4
            )
            Spacer(modifier = Modifier.size(Space24))

            //LOCATION

            StandardTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusEvent {
                        if (it.isFocused) {
                            coroutineScope.launch {
                                delay(200)
                                viewRequester.bringIntoView()
                            }
                        }
                    }
                    .bringIntoViewRequester(viewRequester),
                text = location.text,
                hint = stringResource(id = com.example.kolejka.R.string.location),
                onTextChanged = { viewModel.onEvent(EditPostEvent.EnteredLocation(it)) },
                placeholderTextColor = DarkGray,
                textStyle = MaterialTheme.typography.h3,
                placeholderTextStyle = MaterialTheme.typography.h3,
            )

            Spacer(modifier = Modifier.size(Space24))

            if (type == PostType.Event.type) {

                //IF EVENT
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        modifier = Modifier
                            .clip(RoundedCornerShape(Space12))
                            .background(ExtraLightGray)
                            .padding(
                                Space4
                            ),
                        onClick = { viewModel.onEvent(EditPostEvent.CalendarEnabled(viewModel.showCalendarView.value)) },
                    ) {
                        Text(
                            text = date.value,
                            style = MaterialTheme.typography.h3,
                            color = DarkPurple,
                        )
                    }

                    StandardTextField(
                        modifier = Modifier
                            .width(90.dp)
                            .onFocusEvent {
                                if (it.isFocused) {
                                    coroutineScope.launch {
                                        delay(200)
                                        viewRequester.bringIntoView()
                                    }
                                }
                            }
                            .bringIntoViewRequester(viewRequester),
                        text = limit.text,
                        hint = stringResource(id = com.example.kolejka.R.string.limit),
                        onTextChanged = { viewModel.onEvent(EditPostEvent.EnteredLimit(it)) },
                        placeholderTextColor = DarkGray,
                        textStyle = TextStyle(
                            fontFamily = roboto_mono,
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        ),
                        //placeholderTextAlignment = TextAlign.Justify,
                        placeholderTextStyle = MaterialTheme.typography.h3,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                Spacer(modifier = Modifier.size(Space24))
            }

            if (viewModel.showCalendarView.value) {

                AlertDialog(
                    onDismissRequest = {
                        viewModel.onEvent(
                            EditPostEvent.CalendarEnabled(
                                viewModel.showCalendarView.value
                            )
                        )
                    },
                    text = {
                        AndroidView(
                            {
                                CalendarView(
                                    ContextThemeWrapper(
                                        it,
                                        com.example.kolejka.R.style.CustomCalendar
                                    )
                                ).apply {
                                }
                            },
                            modifier = Modifier.wrapContentSize(),
                            update = { views ->
                                views.setOnDateChangeListener { _, year, month, dayOfMonth ->
                                    viewModel.onEvent(EditPostEvent.SelectDate("$dayOfMonth/${month + 1}/$year"))
                                }
                            })
                    },
                    confirmButton = {
                        Button(onClick = {
                            viewModel.onEvent(
                                EditPostEvent.CalendarEnabled(
                                    viewModel.showCalendarView.value
                                )
                            )
                        }) {
                            Text("Select Date")
                        }
                    },
                    dismissButton = {
                        Button(onClick = {
                            viewModel.onEvent(EditPostEvent.CalendarEnabled(viewModel.showCalendarView.value))
                        }) {
                            Text("Close")
                        }
                    }
                )
            }


            //IF OFFER
            if (type == PostType.Offer.type) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!viewModel.priceVisibility.value) {
                        TextButton(onClick = { viewModel.onEvent(EditPostEvent.SetPriceVisibility) }) {
                            Icon(
                                imageVector = Icons.Default.AttachMoney,
                                tint = MaterialTheme.colors.primary,
                                contentDescription = "Add price"
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(
                                text = stringResource(com.example.kolejka.R.string.add_price),
                                color = MaterialTheme.colors.primary,
                                style = TextStyle(
                                    fontFamily = roboto_mono,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 18.sp,
                                )
                            )
                        }
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            StandardTextField(
                                modifier = Modifier
                                    .width(140.dp)
                                    .onFocusEvent {
                                        if (it.isFocused) {
                                            coroutineScope.launch {
                                                delay(200)
                                                viewRequester.bringIntoView()
                                            }
                                        }
                                    }
                                    .bringIntoViewRequester(viewRequester),
                                text = viewModel.priceState.value.text,
                                hint = stringResource(com.example.kolejka.R.string.price),
                                onTextChanged = { viewModel.onEvent(EditPostEvent.EnteredPrice(it)) },
                                placeholderTextColor = DarkGray,
                                textStyle = TextStyle(
                                    fontFamily = roboto_mono,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 18.sp,
                                    textAlign = TextAlign.Center
                                ),
                                trailingIcon = {
                                    Text(
                                        modifier = Modifier.background(
                                            PostWhite,
                                            shape = CircleShape
                                        ).padding(Space4),
                                        text = stringResource(com.example.kolejka.R.string.czk),
                                        style = MaterialTheme.typography.h5
                                    )
                                },
                                placeholderTextStyle = MaterialTheme.typography.h3,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            IconButton(onClick = {
                                viewModel.onEvent(EditPostEvent.SetPriceVisibility)
                                viewModel.onEvent(EditPostEvent.EnteredPrice(""))
                            }) {
                                Icon(
                                    imageVector = Icons.Default.MoneyOff,
                                    tint = MaterialTheme.colors.primary,
                                    contentDescription = "No price"
                                )
                            }
                        }
                    }

                    //LIMIT


                    StandardTextField(
                        modifier = Modifier
                            .width(90.dp)
                            .onFocusEvent {
                                if (it.isFocused) {
                                    coroutineScope.launch {
                                        delay(200)
                                        viewRequester.bringIntoView()
                                    }
                                }
                            }
                            .bringIntoViewRequester(viewRequester),
                        text = limit.text,
                        hint = stringResource(id = com.example.kolejka.R.string.limit),
                        onTextChanged = { viewModel.onEvent(EditPostEvent.EnteredLimit(it)) },
                        placeholderTextColor = DarkGray,
                        textStyle = TextStyle(
                            fontFamily = roboto_mono,
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        ),
                        //placeholderTextAlignment = TextAlign.Justify,
                        placeholderTextStyle = MaterialTheme.typography.h3,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }

            Spacer(modifier = Modifier.size(Space24))
            if (viewModel.isLoading.value) {

                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = DarkPurple
                )

            } else {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    FloatingAddPostButton(
                        showButton = true,
                        buttonIcon = Icons.Default.Create,
                        buttonText = stringResource(
                            id = com.example.kolejka.R.string.update_post
                        ),
                        iconDescription = "",
                        uploadingImage = viewModel.imageUploading.value
                    ) {
                        viewModel.uploadToCloudinary(localContext)
                    }
                }
            }
        }
    }
}

