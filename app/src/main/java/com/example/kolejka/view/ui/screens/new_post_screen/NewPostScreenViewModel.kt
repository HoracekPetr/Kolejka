package com.example.kolejka.view.ui.screens.new_post_screen

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kolejka.use_cases.post.CreatePostUseCase
import com.example.kolejka.view.util.states.NewPostRadioState
import com.example.kolejka.view.util.states.StandardTextfieldState
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private var _eventRadioState = mutableStateOf(NewPostRadioState())
    var eventRadioState: State<NewPostRadioState> = _eventRadioState

    private var _offerRadioState = mutableStateOf(NewPostRadioState())
    var offerRadioState: State<NewPostRadioState> = _offerRadioState


    fun onEvent(event: NewPostEvent) {
        when (event) {

            is NewPostEvent.PickedImage -> {
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
                _eventRadioState.value = _eventRadioState.value.copy(
                    eventEnabled = true,
                    offerEnabled = false
                )
            }
            is NewPostEvent.OfferPicked -> {
                _offerRadioState.value = _offerRadioState.value.copy(
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

                pickedImageUri.value?.let { uri ->
                    viewModelScope.launch {
                        createPostUseCase(
                            title = titleState.value.text,
                            description = descriptionState.value.text,
                            limit = limitState.value.text.toInt(),
                            type = when (eventRadioState.value.eventEnabled) {
                                true -> 0
                                else -> 1
                            },
                            imageUri = uri
                        )
                    }
                }

            }

        }
    }

}