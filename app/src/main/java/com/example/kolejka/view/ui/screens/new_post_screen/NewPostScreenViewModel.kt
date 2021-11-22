package com.example.kolejka.view.ui.screens.new_post_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewPostScreenViewModel @Inject constructor(): ViewModel() {

    private var _eventRadioEnabled = mutableStateOf(true)
    var eventRadioEnabled: State<Boolean> = _eventRadioEnabled

    private var _offerRadioEnabled = mutableStateOf(false)
    var offerRadioEnabled: State<Boolean> = _offerRadioEnabled

    private var _titleText = mutableStateOf("")
    var titleText: State<String> = _titleText

    private var _descriptionText = mutableStateOf("")
    var descriptionText: State<String> = _descriptionText

    private var _limitText = mutableStateOf("")
    var limitText: State<String> = _limitText

    fun eventRadioEnabled(enabled: Boolean){
        _eventRadioEnabled.value = enabled
        _offerRadioEnabled.value = !enabled
    }

    fun offerRadioEnabled(enabled: Boolean){
        _offerRadioEnabled.value = enabled
        _eventRadioEnabled.value = !enabled
    }

    fun setTitleText(titleText: String){
        _titleText.value = titleText
    }

    fun setDescriptionText(descriptionText: String){
        _descriptionText.value = descriptionText
    }

    fun setLimitText(limitText: String){
        _limitText.value = limitText
    }

}