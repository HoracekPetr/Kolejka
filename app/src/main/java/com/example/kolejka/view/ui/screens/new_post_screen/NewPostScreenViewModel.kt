package com.example.kolejka.view.ui.screens.new_post_screen

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloudinary.Cloudinary
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.kolejka.R
import com.example.kolejka.data.util.Resource
import com.example.kolejka.use_cases.post.NewPostUseCase
import com.example.kolejka.view.util.Constants.NO_DATE_SELECTED
import com.example.kolejka.view.util.PostType
import com.example.kolejka.view.util.Screen
import com.example.kolejka.view.util.UiEvent
import com.example.kolejka.view.util.states.NewPostRadioState
import com.example.kolejka.view.util.states.StandardTextfieldState
import com.example.kolejka.view.util.uitext.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewPostScreenViewModel @Inject constructor(
    private val newPostUseCase: NewPostUseCase
) : ViewModel() {

    private val _pickedImageUri = mutableStateOf<Uri?>(null)
    val pickedImageUri = _pickedImageUri

    private val _imageUrl = mutableStateOf<String?>("")
    val imageUrl = _imageUrl

    private var _titleState = mutableStateOf(StandardTextfieldState())
    var titleState: State<StandardTextfieldState> = _titleState

    private var _descriptionState = mutableStateOf(StandardTextfieldState())
    var descriptionState: State<StandardTextfieldState> = _descriptionState

    private var _locationState = mutableStateOf(StandardTextfieldState())
    var locationState: State<StandardTextfieldState> = _locationState

    private var _limitState = mutableStateOf(StandardTextfieldState())
    var limitState: State<StandardTextfieldState> = _limitState

    private var _optionsRadioState = mutableStateOf(NewPostRadioState())
    var optionsRadioState: State<NewPostRadioState> = _optionsRadioState

    private var _showCalendarView = mutableStateOf(false)
    var showCalendarView: State<Boolean> = _showCalendarView

    private var _selectedDate = mutableStateOf(NO_DATE_SELECTED)
    var selectedDate: State<String> = _selectedDate

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _imageUploading = mutableStateOf(false)
    val imageUploading: State<Boolean> = _imageUploading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val config: HashMap<String, String> = HashMap()

    private val cloudinary = Cloudinary()

    fun onEvent(event: NewPostEvent) {
        when (event) {

            is NewPostEvent.PickedImage -> {
                _pickedImageUri.value = event.uri
            }

            is NewPostEvent.CropImage -> {
                _pickedImageUri.value = event.uri
            }

            is NewPostEvent.EnteredTitle -> {
                _titleState.value = _titleState.value.copy(
                    text = event.title
                )
            }

            is NewPostEvent.EnteredDescription -> {
                _descriptionState.value = _descriptionState.value.copy(
                    text = event.description
                )
            }

            is NewPostEvent.EnteredLocation -> {
                _locationState.value = _locationState.value.copy(
                    text = event.location
                )
            }

            is NewPostEvent.EventPicked -> {
                _optionsRadioState.value = _optionsRadioState.value.copy(
                    eventEnabled = true,
                    offerEnabled = false
                )
            }
            is NewPostEvent.OfferPicked -> {
                _optionsRadioState.value = _optionsRadioState.value.copy(
                    eventEnabled = false,
                    offerEnabled = true
                )
            }

            is NewPostEvent.EnteredLimit -> {
                _limitState.value = _limitState.value.copy(
                    text = event.limit
                )
            }

            is NewPostEvent.CalendarEnabled -> {
                _showCalendarView.value = !event.enabled
            }
            is NewPostEvent.SelectDate -> {
                _selectedDate.value = event.date
            }

            is NewPostEvent.CreatePost -> {

            }
        }
    }

    fun createPost() {
        viewModelScope.launch {

            _isLoading.value = true

            val postResult = newPostUseCase(
                title = titleState.value.text,
                description = descriptionState.value.text,
                limit = limitState.value.text.toIntOrNull(),
                type = when (optionsRadioState.value.eventEnabled) {
                    true -> PostType.Event.type
                    else -> PostType.Offer.type
                },
                date = selectedDate.value,
                location = locationState.value.text,
                postImageURL = _imageUrl.value
            )

            _isLoading.value = false

            when (postResult) {

                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvent.Navigate(Screen.PostScreen.route)
                    )
                }

                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = postResult.uiText ?: UiText.unknownError()
                        )
                    )
                }
            }
        }
    }

    fun cloudinaryUpload(uri: Uri?) {
        viewModelScope.launch {
            if (uri == null) {
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(
                        uiText = UiText.StringResource(R.string.pick_an_image)
                    )
                )
            } else if (
                _titleState.value.text.isNotBlank() &&
                _descriptionState.value.text.isNotBlank() &&
                _locationState.value.text.isNotBlank() &&
                _limitState.value.text.isNotBlank()
            ) {
                if(_optionsRadioState.value.offerEnabled || _selectedDate.value != NO_DATE_SELECTED){
                    val requestId = MediaManager.get().upload(uri).callback(object : UploadCallback {
                        override fun onStart(requestId: String?) {
                            _isLoading.value = true
                        }

                        override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                            _isLoading.value = true
                        }

                        override fun onSuccess(
                            requestId: String?,
                            resultData: MutableMap<Any?, Any?>?
                        ) {
                            println("CLOUDINARY SUCCESS")
                            _isLoading.value = false
                            _imageUrl.value = resultData?.getValue("secure_url").toString()
                            createPost()
                        }

                        override fun onError(requestId: String?, error: ErrorInfo?) {
                            println("CLOUDINARY FAIL")
                            _isLoading.value = false
                        }

                        override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                        }
                    }).dispatch()
                }
                else{
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = UiText.StringResource(R.string.select_a_date)
                        )
                    )
                }
            } else{
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(
                        uiText = UiText.StringResource(R.string.fields_blank)
                    )
                )
            }
        }
    }
}