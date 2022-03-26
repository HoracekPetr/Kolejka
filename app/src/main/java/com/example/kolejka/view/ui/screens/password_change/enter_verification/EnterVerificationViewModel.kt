package com.example.kolejka.view.ui.screens.password_change.enter_verification

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kolejka.R
import com.example.kolejka.view.ui.screens.password_change.change_password.ChangePasswordScreen
import com.example.kolejka.view.util.Screen
import com.example.kolejka.view.util.UiEvent
import com.example.kolejka.view.util.states.StandardTextfieldState
import com.example.kolejka.view.util.uitext.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnterVerificationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _codeState = mutableStateOf(StandardTextfieldState())
    val codeState: State<StandardTextfieldState> = _codeState

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _userId = mutableStateOf<String?>(null)
    val userId: State<String?> = _userId

    private val _verificationCode = mutableStateOf<String?>(null)
    val verificationCode: State<String?> = _verificationCode

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        _userId.value = savedStateHandle.get<String>("userId")
        _verificationCode.value = savedStateHandle.get<String>("code")
    }

    fun onEvent(event: EnterVerificationEvent){
        when(event){
            is EnterVerificationEvent.EnteredCode -> {
                _codeState.value = _codeState.value.copy(
                    text = event.code
                )
            }
            is EnterVerificationEvent.CheckCode -> {
                viewModelScope.launch {
                    checkVerificationCode()
                }
            }
        }
    }


    private suspend fun checkVerificationCode(){

        _isLoading.value = true

        delay(1000)

        if(_codeState.value.text == _verificationCode.value){
            _eventFlow.emit(
                UiEvent.ShowSnackbarAndNavigate(
                    uiText = UiText.StringResource(R.string.correct_code),
                    route = Screen.ChangePasswordScreen.route + "?userId=${userId.value}"
                )
            )
            _isLoading.value = false
        } else {
            _eventFlow.emit(
                UiEvent.ShowSnackbar(
                    uiText = UiText.StringResource(R.string.incorrect_code)
                )
            )
            _isLoading.value = false
        }
    }
}