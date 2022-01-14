package com.example.kolejka.view.ui.screens.login_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kolejka.data.util.Resource
import com.example.kolejka.use_cases.auth.AuthenticateUseCase
import com.example.kolejka.use_cases.auth.LoginUseCase
import com.example.kolejka.view.util.Screen
import com.example.kolejka.view.util.UiEvent
import com.example.kolejka.view.util.states.PasswordTextfieldState
import com.example.kolejka.view.util.states.StandardTextfieldState
import com.example.kolejka.view.util.uitext.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _emailState = mutableStateOf(StandardTextfieldState())
    val emailState: State<StandardTextfieldState> = _emailState

    private val _passwordState = mutableStateOf(PasswordTextfieldState())
    val passwordState: State<PasswordTextfieldState> = _passwordState

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.ChangePasswordVisibility -> {
                _passwordState.value = _passwordState.value.copy(
                    visible = event.visibility
                )
            }
            is LoginEvent.EnteredEmail -> {
                _emailState.value = _emailState.value.copy(
                    text = event.email
                )
            }
            is LoginEvent.EnteredPassword -> {
                _passwordState.value = _passwordState.value.copy(
                    text = event.password
                )
            }
            LoginEvent.Login -> {
                login()
            }
        }
    }

    private fun login() {
        viewModelScope.launch {

            _isLoading.value = true

            _emailState.value = _emailState.value.copy(
                error = null
            )

            _passwordState.value = _passwordState.value.copy(
                error = null
            )

            val loginResult = loginUseCase(
                email = emailState.value.text,
                password = passwordState.value.text
            )

            _isLoading.value = false

            if (loginResult.emailError != null) {
                _emailState.value = _emailState.value.copy(
                    error = loginResult.emailError
                )
            }

            if (loginResult.passwordError != null) {
                _passwordState.value = _passwordState.value.copy(
                    error = loginResult.passwordError
                )
            }

            when (loginResult.result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvent.Navigate(Screen.PostScreen.route)
                    )

                    _emailState.value = StandardTextfieldState()
                    _passwordState.value = PasswordTextfieldState()
                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(uiText = loginResult.result.uiText ?: UiText.unknownError())
                    )
                }
                null -> {
                    _isLoading.value = false
                }
            }
        }
    }
}

