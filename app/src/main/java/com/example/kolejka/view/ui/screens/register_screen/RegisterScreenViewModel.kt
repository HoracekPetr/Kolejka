package com.example.kolejka.view.ui.screens.register_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterScreenViewModel @Inject constructor() : ViewModel() {

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _username = mutableStateOf("")
    val username: State<String> = _username

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _passwordVisibility = mutableStateOf(false)
    val passwordVisibility: State<Boolean> = _passwordVisibility

    private val _incorrectEmailCheck = mutableStateOf(false)
    val incorrectEmailCheck: State<Boolean> = _incorrectEmailCheck

    //////////////

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setUsername(username: String) {
        _username.value = username
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun setPasswordVisibility(){
        _passwordVisibility.value = !_passwordVisibility.value
    }

    fun checkEmailValidity(): Boolean{
        return _email.value.endsWith("tul.cz")
    }

    fun setEmailCheck(isIncorrect: Boolean){
        _incorrectEmailCheck.value = isIncorrect
    }
}