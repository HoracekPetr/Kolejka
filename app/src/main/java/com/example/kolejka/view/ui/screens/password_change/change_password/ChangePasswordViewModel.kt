package com.example.kolejka.view.ui.screens.password_change.change_password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kolejka.R
import com.example.kolejka.data.util.Resource
import com.example.kolejka.use_cases.user.ChangePasswordUseCase
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
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _newPasswordState = mutableStateOf(PasswordTextfieldState())
    val newPasswordState: State<PasswordTextfieldState> = _newPasswordState

    private val _newPasswordAgainState = mutableStateOf(PasswordTextfieldState())
    val newPasswordAgainState: State<PasswordTextfieldState> = _newPasswordAgainState

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var userId: String? = null

    init {
        userId = savedStateHandle.get<String>("userId")
    }

    fun onEvent(event: ChangePasswordEvent) {
        when (event) {
            is ChangePasswordEvent.EnteredNewPassword -> {
                _newPasswordState.value = _newPasswordState.value.copy(
                    text = event.password
                )
            }
            is ChangePasswordEvent.EnteredNewPasswordAgain -> {
                _newPasswordAgainState.value = _newPasswordAgainState.value.copy(
                    text = event.passwordAgain
                )
            }

            is ChangePasswordEvent.ChangePasswordVisibility -> {
                _newPasswordState.value = _newPasswordState.value.copy(
                    visible = event.visibility
                )
            }

            is ChangePasswordEvent.ChangePasswordAgainVisibility -> {
                _newPasswordAgainState.value = _newPasswordAgainState.value.copy(
                    visible = event.visibility
                )
            }

            ChangePasswordEvent.ChangePassword -> {
                changePassword()
            }
        }
    }

    private fun changePassword() {
        _isLoading.value = true

        viewModelScope.launch {
            if (userId != null) {
                _newPasswordState.value = _newPasswordState.value.copy(
                    error = null
                )

                _newPasswordAgainState.value = _newPasswordAgainState.value.copy(
                    error = null
                )

                val changePasswordResult = changePasswordUseCase(
                    userId = userId,
                    newPassword = newPasswordState.value.text,
                    newPasswordAgain = newPasswordAgainState.value.text
                )


                if (changePasswordResult.newPasswordError != null) {
                    _newPasswordState.value = _newPasswordState.value.copy(
                        error = changePasswordResult.newPasswordError
                    )
                }

                if (changePasswordResult.newPasswordAgainError != null) {
                    _newPasswordAgainState.value = _newPasswordAgainState.value.copy(
                        error = changePasswordResult.newPasswordAgainError
                    )
                }

                if (changePasswordResult.passwordNotMatching) {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(uiText = UiText.StringResource(R.string.password_dont_match))
                    )
                }

                when (changePasswordResult.changePasswordResult) {

                    is Resource.Error -> {
                        _isLoading.value = false

                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                changePasswordResult.changePasswordResult.uiText
                                    ?: UiText.unknownError()
                            )
                        )
                    }
                    is Resource.Success -> {
                        _isLoading.value = false

                        _eventFlow.emit(
                            UiEvent.ShowSnackbarAndNavigate(
                                UiText.StringResource(R.string.password_changed),
                                route = Screen.LoginScreen.route
                            )
                        )

                        _newPasswordState.value = PasswordTextfieldState()
                        _newPasswordAgainState.value = PasswordTextfieldState()
                    }
                    null -> {
                        _isLoading.value = false
                    }
                }
            } else {
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(
                        UiText.StringDynamic(text = "User doesn't exist")
                    )
                )
            }
        }
    }
}