package com.example.kolejka.view.ui.screens.register_screen

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.kolejka.view.util.Constants.MIN_PASSWORD_LENGTH
import com.example.kolejka.view.util.Constants.MIN_USERNAME_LENGTH
import com.example.kolejka.view.util.errors.AuthError
import com.example.kolejka.view.util.states.PasswordTextfieldState
import com.example.kolejka.view.util.states.StandardTextfieldState
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

    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    private val _emailState = mutableStateOf(StandardTextfieldState())
    val emailState: State<StandardTextfieldState> = _emailState

    private val _usernameState = mutableStateOf(StandardTextfieldState())
    val usernameState: State<StandardTextfieldState> = _usernameState

    private val _passwordState = mutableStateOf(PasswordTextfieldState())
    val passwordState: State<PasswordTextfieldState> = _passwordState

    fun onEvent(event: RegisterEvent) {
        when(event){

            is RegisterEvent.EnteredEmail -> {
                _emailState.value = _emailState.value.copy(
                    text = event.email
                )
            }

            is RegisterEvent.EnteredUsername -> {
                _usernameState.value = _usernameState.value.copy(
                    text = event.username
                )
            }

            is RegisterEvent.EnteredPassword -> {
                _passwordState.value = _passwordState.value.copy(
                    text = event.password
                )
            }

            is RegisterEvent.ChangePasswordVisibility -> {
                _passwordState.value = _passwordState.value.copy(
                    visible = event.visibility
                )
            }
            RegisterEvent.Register -> {
                validateUsername(
                    usernameState.value.text
                )
                validateEmail(
                    emailState.value.text
                )
                validatePassword(
                    passwordState.value.text
                )
            }
        }
    }

    private fun validateEmail(email: String){
        val trimEmail = email.trim()
        if(trimEmail.isBlank()){
            _emailState.value = _emailState.value.copy(
                error = AuthError.EmptyField
            )
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            _emailState.value = _emailState.value.copy(
                error = AuthError.InvalidEmail
            )
            return
        } else {
            _emailState.value = _emailState.value.copy(
                error = null
            )
        }
    }

    private fun validateUsername(username: String){
        val trimUsername = username.trim()
        if(trimUsername.isBlank()){
            _usernameState.value = _usernameState.value.copy(
                error = AuthError.EmptyField
            )
            return
        }

        if(trimUsername.length < MIN_USERNAME_LENGTH){
            _usernameState.value = _usernameState.value.copy(
                error = AuthError.InputTooShort
            )
            return
        } else {
            _usernameState.value = _usernameState.value.copy(
                error = null
            )
        }
    }


    private fun validatePassword(password: String){
        val trimPassword = password.trim()
        if(trimPassword.isBlank()){
            _passwordState.value = _passwordState.value.copy(
                error = AuthError.EmptyField
            )
            return
        }

        if(trimPassword.length < MIN_PASSWORD_LENGTH){
            _passwordState.value = _passwordState.value.copy(
                error = AuthError.InputTooShort
            )
            return
        }

        if(!trimPassword.contains(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]+\$"))){
            _passwordState.value = _passwordState.value.copy(
                error = AuthError.InvalidPassword
            )
        } else {
            _passwordState.value = _passwordState.value.copy(
                error = null
            )
        }

    }
}