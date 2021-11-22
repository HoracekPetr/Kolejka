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

    private var _yourPostsRadioEnabled = mutableStateOf(true)
    var yourPostsRadioEnabled: State<Boolean> = _yourPostsRadioEnabled

    private var _joinedPostsRadioEnabled = mutableStateOf(false)
    var joinedPostsRadioEnabled: State<Boolean> = _joinedPostsRadioEnabled


    fun setEditProfileDialogEnabled(enabled: Boolean){
        _showEditProfileDialog.value = enabled
    }

    fun setYourPostsRadioEnabled(enabled: Boolean){
        _yourPostsRadioEnabled.value = enabled
        _joinedPostsRadioEnabled.value = !enabled
    }

    fun setJoinedPostsRadioEnabled(enabled: Boolean){
        _joinedPostsRadioEnabled.value = enabled
        _yourPostsRadioEnabled.value = !enabled
    }

}