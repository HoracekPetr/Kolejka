package com.example.kolejka.view.ui.screens.profile_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor() : ViewModel(){

    private var _showEditProfileDialog = mutableStateOf(false)
    var showEditProfileDialog: State<Boolean> = _showEditProfileDialog

    private var _eventsRadioEnabled = mutableStateOf(true)
    var eventsRadioEnabled: State<Boolean> = _eventsRadioEnabled

    private var _offersRadioEnabled = mutableStateOf(false)
    var offersRadioEnabled: State<Boolean> = _offersRadioEnabled


    fun setEditProfileDialogEnabled(enabled: Boolean){
        _showEditProfileDialog.value = enabled
    }

    fun setYourPostsRadioEnabled(enabled: Boolean){
        _eventsRadioEnabled.value = enabled
        _offersRadioEnabled.value = !enabled
    }

    fun setJoinedPostsRadioEnabled(enabled: Boolean){
        _offersRadioEnabled.value = enabled
        _eventsRadioEnabled.value = !enabled
    }

}