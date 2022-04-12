package com.example.kolejka.view.ui.screens.new_post_screen

import android.util.Log
import android.widget.CalendarView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Event
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.kolejka.R
import com.example.kolejka.view.theme.*
import com.example.kolejka.view.ui.components.StandardTextField
import com.example.kolejka.view.ui.components.bottom_navigation.FloatingAddPostButton
import com.example.kolejka.view.util.Constants.DESC_MAX_CHARS
import com.example.kolejka.view.util.Constants.NO_DATE_SELECTED
import com.example.kolejka.view.util.Screen
import com.example.kolejka.view.util.UiEvent
import com.example.kolejka.view.util.crop.CropActivityResultContract
import com.example.kolejka.view.util.uitext.asString
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun NewPostScreen(
    navController: NavController,
    viewModel: NewPostScreenViewModel = hiltViewModel()
) {
    val localFocusManager = LocalFocusManager.current
    val localContext = LocalContext.current
    val viewRequester = BringIntoViewRequester()

    val coroutineScope = rememberCoroutineScope()

    val interactionSource = remember { MutableInteractionSource() }
    val scaffoldState = rememberScaffoldState()

    val optionsRadio = viewModel.optionsRadioState

    val imageUri = viewModel.pickedImageUri

    val title = viewModel.titleState
    val description = viewModel.descriptionState
    val location = viewModel.locationState
    var date = viewModel.selectedDate
    val limit = viewModel.limitState
    val url = viewModel.imageUrl

    val scrollState = rememberScrollState()

    val context = LocalContext.current


    val cropActivityLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(aspectX = 16f, aspectY = 9f)
    ) {
        Log.d("URI IS", "$it")
        viewModel.onEvent(NewPostEvent.CropImage(it))
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
            //Add Post Picture
            Box(
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
                    }
            ) {
                Icon(
                    modifier = Modifier.align(Alignment.Center).size(75.dp),
                    tint = MaterialTheme.colors.onSurface,
                    imageVector = Icons.Default.AddAPhoto,
                    contentDescription = stringResource(R.string.add_a_new_photo)
                )

                imageUri.value?.let { uri ->
                    Image(
                        modifier = Modifier
                            .matchParentSize()
                            .aspectRatio(16f / 9f),
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
                            unselectedColor = MaterialTheme.colors.secondaryVariant
                        ),
                        onClick = { viewModel.onEvent(NewPostEvent.EventPicked) })
                    Text(
                        text = stringResource(R.string.event),
                        style = MaterialTheme.typography.subtitle2,
                        color = if (optionsRadio.value.eventEnabled) DarkPurple else MaterialTheme.colors.secondaryVariant
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
                            unselectedColor = MaterialTheme.colors.secondaryVariant
                        ),
                        onClick = { viewModel.onEvent(NewPostEvent.OfferPicked) })

                    Text(
                        stringResource(R.string.offer),
                        style = MaterialTheme.typography.subtitle2,
                        color = if (optionsRadio.value.offerEnabled) DarkPurple else MaterialTheme.colors.secondaryVariant
                    )
                }
            }
            Spacer(modifier = Modifier.size(Space12))

            ////////////////////////////////////////

            if (optionsRadio.value.eventEnabled) {

                //TITLE

                StandardTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = title.value.text,
                    hint = stringResource(R.string.title),
                    textStyle = MaterialTheme.typography.body1,
                    onTextChanged = { viewModel.onEvent(NewPostEvent.EnteredTitle(it)) },
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
                    text = description.value.text,
                    hint = stringResource(id = R.string.description),
                    onTextChanged = {
                        if (it.length <= DESC_MAX_CHARS) {
                            viewModel.onEvent(
                                NewPostEvent.EnteredDescription(it)
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
                    text = location.value.text,
                    hint = stringResource(id = R.string.location),
                    onTextChanged = { viewModel.onEvent(NewPostEvent.EnteredLocation(it)) },
                    placeholderTextColor = DarkGray,
                    textStyle = MaterialTheme.typography.h3,
                    placeholderTextStyle = MaterialTheme.typography.h3,
                )

                Spacer(modifier = Modifier.size(Space24))

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
                        onClick = { viewModel.onEvent(NewPostEvent.CalendarEnabled(viewModel.showCalendarView.value)) },
                    ) {
                        if (viewModel.selectedDate.value == stringResource(R.string.no_selected_date)) {
                            Icon(imageVector = Icons.Default.Event, contentDescription = "")
                            Text(text = stringResource(id = R.string.no_selected_date))
                        } else {
                            Text(
                                text = viewModel.selectedDate.value,
                                style = MaterialTheme.typography.h3,
                                color = DarkPurple,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(Space24))
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
                        //placeholderTextAlignment = TextAlign.Justify,
                        placeholderTextStyle = MaterialTheme.typography.h3,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                if (viewModel.showCalendarView.value) {

                    AlertDialog(
                        onDismissRequest = {
                            viewModel.onEvent(
                                NewPostEvent.CalendarEnabled(
                                    viewModel.showCalendarView.value
                                )
                            )
                        },
                        text = {
                            AndroidView(
                                {
                                    CalendarView(
                                        android.view.ContextThemeWrapper(
                                            it,
                                            R.style.CustomCalendar
                                        )
                                    ).apply {
                                    }
                                },
                                modifier = Modifier.wrapContentSize(),
                                update = { views ->
                                    views.setOnDateChangeListener { _, year, month, dayOfMonth ->
                                        viewModel.onEvent(NewPostEvent.SelectDate("$dayOfMonth/${month + 1}/$year"))
                                    }
                                })
                        },
                        confirmButton = {
                            Button(onClick = {
                                viewModel.onEvent(
                                    NewPostEvent.CalendarEnabled(
                                        viewModel.showCalendarView.value
                                    )
                                )
                            }) {
                                Text("Select Date")
                            }
                        },
                        dismissButton = {
                            Button(onClick = {
                                viewModel.onEvent(NewPostEvent.CalendarEnabled(viewModel.showCalendarView.value))
                                viewModel.onEvent(NewPostEvent.SelectDate(NO_DATE_SELECTED))
                            }) {
                                Text("Close")
                            }
                        }
                    )
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
                                id = R.string.create_the_event
                            ),
                            iconDescription = "",
                            uploadingImage = viewModel.imageUploading.value
                        ) {
                            viewModel.cloudinaryUpload(imageUri.value, context)
                        }
                    }
                }

                ////////////////////////////////////////
                //////////////OFFER////////////////////
                ///////////////////////////////////////


            } else if (optionsRadio.value.offerEnabled) {

                //TITLE

                StandardTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = title.value.text,
                    hint = stringResource(R.string.title),
                    textStyle = MaterialTheme.typography.body1,
                    onTextChanged = { viewModel.onEvent(NewPostEvent.EnteredTitle(it)) },
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
                    text = description.value.text,
                    hint = stringResource(id = R.string.description),
                    onTextChanged = {
                        if (it.length <= DESC_MAX_CHARS) {
                            viewModel.onEvent(
                                NewPostEvent.EnteredDescription(it)
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
                    text = location.value.text,
                    hint = stringResource(id = R.string.location),
                    onTextChanged = { viewModel.onEvent(NewPostEvent.EnteredLocation(it)) },
                    placeholderTextColor = DarkGray,
                    textStyle = MaterialTheme.typography.h3,
                    placeholderTextStyle = MaterialTheme.typography.h3,
                )

                Spacer(modifier = Modifier.size(Space24))


                //LIMIT

                StandardTextField(
                    modifier = Modifier
                        .width(90.dp)
                        .align(Alignment.CenterHorizontally)
                        .onFocusEvent {
                            if (it.isFocused) {
                                coroutineScope.launch {
                                    delay(200)
                                    viewRequester.bringIntoView()
                                }
                            }
                        }
                        .bringIntoViewRequester(viewRequester),
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
                    //placeholderTextAlignment = TextAlign.Justify,
                    placeholderTextStyle = MaterialTheme.typography.h3,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                //}

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
                                id = R.string.create_the_offer
                            ),
                            iconDescription = ""
                        ) {
                            viewModel.cloudinaryUpload(imageUri.value, context)
                        }
                    }
                }
            }
        }
    }
}

