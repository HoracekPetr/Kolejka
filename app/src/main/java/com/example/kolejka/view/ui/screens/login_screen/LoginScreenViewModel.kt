package com.example.kolejka.view.ui.screens.login_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(): ViewModel() {

    private val _usernameText = mutableStateOf("")
    val usernameText: State<String> = _usernameText

    private val _passwordText = mutableStateOf("")
    val passwordText: State<String> = _passwordText

    private val _passwordVisibility = mutableStateOf(false)
    val passwordVisibility: State<Boolean> = _passwordVisibility

    fun setUsernameText(username: String){
        _usernameText.value = username
    }

    fun setPasswordText(password: String){
        _passwordText.value = password
    }

    fun setPasswordVisibility(){
        _passwordVisibility.value = !_passwordVisibility.value
    }
}