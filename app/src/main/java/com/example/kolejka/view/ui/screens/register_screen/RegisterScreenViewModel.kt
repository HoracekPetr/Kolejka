package com.example.kolejka.view.ui.screens.register_screen

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kolejka.R
import com.example.kolejka.data.util.Resource
import com.example.kolejka.use_cases.auth.RegisterUseCase
import com.example.kolejka.view.util.Constants.MIN_PASSWORD_LENGTH
import com.example.kolejka.view.util.Constants.MIN_USERNAME_LENGTH
import com.example.kolejka.view.util.UiEvent
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
            is RegisterEvent.Register -> {
                register()
            }
        }
    }

    private fun register(){

        viewModelScope.launch {

            _emailState.value = _emailState.value.copy(
                error = null
            )
            _usernameState.value = _usernameState.value.copy(
                error = null
            )
            _passwordState.value = _passwordState.value.copy(
                error = null
            )


            val registerResult = registerUseCase(
                email = emailState.value.text,
                username = usernameState.value.text,
                password = passwordState.value.text
            )

            if(registerResult.emailError != null){
                _emailState.value = _emailState.value.copy(
                    error = registerResult.emailError
                )
            }
            if(registerResult.usernameError != null){
                _usernameState.value = _usernameState.value.copy(
                    error = registerResult.usernameError
                )
            }
            if(registerResult.passwordError != null){
                _passwordState.value = _passwordState.value.copy(
                    error = registerResult.passwordError
                )
            }

            _registerState.value = RegisterState(
                isLoading = true
            )

            when(registerResult.result){
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvent.SnackbarEvent(UiText.StringResource(R.string.successful_registration))
                    )
                    _registerState.value = RegisterState(
                        isLoading = false
                    )
                    _emailState.value = StandardTextfieldState()
                    _usernameState.value = StandardTextfieldState()
                    _passwordState.value = PasswordTextfieldState()
                }
                is Resource.Error -> {
                    _registerState.value = RegisterState(
                        isLoading = false
                    )
                    _eventFlow.emit(
                        UiEvent.SnackbarEvent(registerResult.result.uiText ?: UiText.unknownError())
                    )
                }
                null -> {
                    _registerState.value = RegisterState(
                        isLoading = false
                    )
                }
            }
        }
    }
}
