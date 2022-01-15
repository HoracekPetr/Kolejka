package com.example.kolejka.view.ui.screens.new_post_screen

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kolejka.data.util.Resource
import com.example.kolejka.use_cases.post.CreatePostUseCase
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
    private val createPostUseCase: CreatePostUseCase
) : ViewModel() {

    private val _pickedImageUri = mutableStateOf<Uri?>(null)
    val pickedImageUri = _pickedImageUri

    private var _titleText = mutableStateOf(StandardTextfieldState())
    var titleState: State<StandardTextfieldState> = _titleText

    private var _descriptionState = mutableStateOf(StandardTextfieldState())
    var descriptionState: State<StandardTextfieldState> = _descriptionState

    private var _limitState = mutableStateOf(StandardTextfieldState())
    var limitState: State<StandardTextfieldState> = _limitState

    private var _optionsRadioState = mutableStateOf(NewPostRadioState())
    var optionsRadioState: State<NewPostRadioState> = _optionsRadioState

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun onEvent(event: NewPostEvent) {
        when (event) {

            is NewPostEvent.PickedImage -> {
                _pickedImageUri.value = event.uri
            }

            is NewPostEvent.CropImage -> {
                _pickedImageUri.value = event.uri
            }

            is NewPostEvent.EnteredTitle -> {
                _titleText.value = _titleText.value.copy(
                    text = event.title
                )
            }

            is NewPostEvent.EnteredDescription -> {
                _descriptionState.value = _descriptionState.value.copy(
                    text = event.description
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

            is NewPostEvent.CreatePost -> {
                viewModelScope.launch {

                    _isLoading.value = true

                    val postResult = createPostUseCase(
                        title = titleState.value.text,
                        description = descriptionState.value.text,
                        limit = limitState.value.text.toIntOrNull(),
                        type = when (optionsRadioState.value.eventEnabled) {
                            true -> 0
                            else -> 1
                        },
                        imageUri = pickedImageUri.value
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
        }
    }
}