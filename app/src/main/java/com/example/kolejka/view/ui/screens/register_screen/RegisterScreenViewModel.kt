package com.example.kolejka.view.ui.screens.register_screen

import android.util.Patterns
import androidx.annotation.StringRes
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kolejka.R
import com.example.kolejka.data.util.Resource
import com.example.kolejka.use_cases.RegisterUseCase
import com.example.kolejka.view.util.Constants.MIN_PASSWORD_LENGTH
import com.example.kolejka.view.util.Constants.MIN_USERNAME_LENGTH
import com.example.kolejka.view.util.errors.Errors
import com.example.kolejka.view.util.states.PasswordTextfieldState
import com.example.kolejka.view.util.states.StandardTextfieldState
import com.example.kolejka.view.util.uitext.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterScreenViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _emailState = mutableStateOf(StandardTextfieldState())
    val emailState: State<StandardTextfieldState> = _emailState

    private val _usernameState = mutableStateOf(StandardTextfieldState())
    val usernameState: State<StandardTextfieldState> = _usernameState

    private val _passwordState = mutableStateOf(PasswordTextfieldState())
    val passwordState: State<PasswordTextfieldState> = _passwordState

    private val _registerState = mutableStateOf(RegisterState())
    val registerState: State<RegisterState> = _registerState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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
                register()
            }
        }
    }

    private fun register(){

        if(usernameState.value.error != null || emailState.value.error != null || passwordState.value.error != null){
            return
        }

        viewModelScope.launch {
            _registerState.value = RegisterState(
                isLoading = true
            )
            val result = registerUseCase(
                email = emailState.value.text,
                username = usernameState.value.text,
                password = passwordState.value.text
            )
            when(result){
                is Resource.Success -> {
                    _registerState.value = RegisterState(
                        isLoading = false
                    )

                    _eventFlow.emit(
                        UiEvent.SnackbarEvent(UiText.StringResource(R.string.successful_registration))
                    )
                }
                is Resource.Error -> {
                    _registerState.value = RegisterState(
                        isLoading = false
                    )
                    _eventFlow.emit(
                        UiEvent.SnackbarEvent(result.uiText ?: UiText.unknownError())
                    )
                }
            }
        }
    }

    private fun validateEmail(email: String){
        val trimEmail = email.trim()
        if(trimEmail.isBlank()){
            _emailState.value = _emailState.value.copy(
                error = Errors.EmptyField
            )
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            _emailState.value = _emailState.value.copy(
                error = Errors.InvalidEmail
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
                error = Errors.EmptyField
            )
            return
        }

        if(trimUsername.length < MIN_USERNAME_LENGTH){
            _usernameState.value = _usernameState.value.copy(
                error = Errors.InputTooShort
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
                error = Errors.EmptyField
            )
            return
        }

        if(trimPassword.length < MIN_PASSWORD_LENGTH){
            _passwordState.value = _passwordState.value.copy(
                error = Errors.InputTooShort
            )
            return
        }

        if(!trimPassword.contains(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]+\$"))){
            _passwordState.value = _passwordState.value.copy(
                error = Errors.InvalidPassword
            )
        } else {
            _passwordState.value = _passwordState.value.copy(
                error = null
            )
        }
    }
}

sealed class UiEvent{
    data class SnackbarEvent(val uiText: UiText): UiEvent()
}