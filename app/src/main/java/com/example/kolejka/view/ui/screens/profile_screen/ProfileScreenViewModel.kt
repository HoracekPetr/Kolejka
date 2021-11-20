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

    fun setEditProfileDialogEnabled(enabled: Boolean){
        _showEditProfileDialog.value = enabled
    }

}