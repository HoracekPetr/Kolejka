package com.example.kolejka.view.ui.screens.register_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.nedim.maildroidx.MaildroidXType
import co.nedim.maildroidx.callback
import co.nedim.maildroidx.sendEmail
import com.example.kolejka.R
import com.example.kolejka.data.util.Resource
import com.example.kolejka.use_cases.auth.RegisterUseCase
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

    private val _verificationCode = mutableStateOf<String?>(null)
    val verificationCode: State<String?> = _verificationCode

    private val _emailState = mutableStateOf(StandardTextfieldState())
    val emailState: State<StandardTextfieldState> = _emailState

    private val _usernameState = mutableStateOf(StandardTextfieldState())
    val usernameState: State<StandardTextfieldState> = _usernameState

    private val _passwordState = mutableStateOf(PasswordTextfieldState())
    val passwordState: State<PasswordTextfieldState> = _passwordState

    private val _registerState = mutableStateOf(RegisterState())
    val registerState: State<RegisterState> = _registerState

    private val _verificationCodeState = mutableStateOf(StandardTextfieldState())
    val verificationCodeState: State<StandardTextfieldState> = _verificationCodeState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {

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

            is RegisterEvent.EnteredVerificationCode -> {
                _verificationCodeState.value = _verificationCodeState.value.copy(
                    text = event.code
                )
            }

            is RegisterEvent.SendVerificationCode -> {
                sendVerificationCode()
            }

            is RegisterEvent.Register -> {
                register()
            }
        }
    }

    private fun register() {

        _registerState.value = _registerState.value.copy(
            isLoading = true
        )

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

            _verificationCodeState.value = _verificationCodeState.value.copy(
                error = null
            )

            val registerResult = registerUseCase(
                email = emailState.value.text,
                username = usernameState.value.text,
                password = passwordState.value.text,
                inputCode = verificationCodeState.value.text,
                correctCode = _verificationCode.value ?: ""
            )

            if (registerResult.emailError != null) {
                _emailState.value = _emailState.value.copy(
                    error = registerResult.emailError
                )
            }
            if (registerResult.usernameError != null) {
                _usernameState.value = _usernameState.value.copy(
                    error = registerResult.usernameError
                )
            }
            if (registerResult.passwordError != null) {
                _passwordState.value = _passwordState.value.copy(
                    error = registerResult.passwordError
                )
            }

            if(registerResult.codeError != null) {
                _verificationCodeState.value = _verificationCodeState.value.copy(
                    error = registerResult.codeError
                )
            }

            if(registerResult.codesNotMatching){
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(UiText.StringResource(R.string.wrong_code))
                )
            }

            when (registerResult.result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(UiText.StringResource(R.string.successful_registration))
                    )
                    _registerState.value = _registerState.value.copy(
                        isLoading = false
                    )
                    _emailState.value = StandardTextfieldState()
                    _usernameState.value = StandardTextfieldState()
                    _passwordState.value = PasswordTextfieldState()
                    _verificationCodeState.value = StandardTextfieldState()
                    _verificationCode.value = null
                }
                is Resource.Error -> {
                    _registerState.value = _registerState.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(registerResult.result.uiText ?: UiText.unknownError())
                    )

                    _verificationCode.value = null
                }
                null -> {
                    _registerState.value = RegisterState(
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun sendVerificationCode() {

        _registerState.value = _registerState.value.copy(
            isLoading = true
        )

        _verificationCode.value = getRandomString(6).uppercase()

        sendEmail {
            isStartTLSEnabled(true)
            smtp("rumburak.mydreams.cz")
            smtpUsername("kolejka@kolejka-app.eu")
            smtpPassword("aaxzyULQPF8#5")
            port("587")
            type(MaildroidXType.HTML)
            to(_emailState.value.text)
            from("verifikace@kolejka-app.eu")
            subject("Ověřovací kód aplikace Kolejka")
            body("<h4>Váš ověřovací kód aplikace:</h4><h2>${_verificationCode.value}</h2><br><p>---------------------</p><p>Tým Kolejka<p>")
            callback {
                timeOut(100)
                onSuccess {
                    Log.d("MaildroidX", "SUCCESS")
                    _registerState.value = _registerState.value.copy(
                        isLoading = false
                    )
                    viewModelScope.launch {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(UiText.sentMail())
                        )
                    }
                }
                onFail {
                    Log.d("MaildroidX", "FAIL")
                    _registerState.value = _registerState.value.copy(
                        isLoading = false
                    )
                    viewModelScope.launch {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(UiText.cantSendMail())
                        )
                    }
                }
            }

        }
    }

    private fun getRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}
