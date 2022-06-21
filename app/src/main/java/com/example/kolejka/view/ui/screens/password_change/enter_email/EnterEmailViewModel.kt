package com.example.kolejka.view.ui.screens.password_change.enter_email

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
import com.example.kolejka.use_cases.user.GetUserIdUseCase
import com.example.kolejka.view.util.Screen
import com.example.kolejka.view.util.UiEvent
import com.example.kolejka.view.util.states.StandardTextfieldState
import com.example.kolejka.view.util.uitext.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnterEmailViewModel @Inject constructor(
    private val getUserIdUseCase: GetUserIdUseCase
): ViewModel() {

    private val _emailState = mutableStateOf(StandardTextfieldState())
    val emailState: State<StandardTextfieldState> = _emailState

    private val _verificationCode = mutableStateOf("")
    private val verificationCode: State<String> = _verificationCode

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: EnterEmailEvent){
        when(event){
            is EnterEmailEvent.EnteredEmail -> {
                _emailState.value = _emailState.value.copy(
                    text = event.email
                )
            }
            is EnterEmailEvent.CheckEmail -> {
                checkEmail()
            }
        }
    }

    private fun checkEmail(){
        viewModelScope.launch {

            _isLoading.value = true

            when(val result = getUserIdUseCase(_emailState.value.text)){
                is Resource.Error -> {
                    println("RESULT ${result.data}")
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(uiText = UiText.StringResource(R.string.email_doesnt_exist))
                    )
                    _isLoading.value = false
                }
                is Resource.Success -> {
                    println("SUCCESS, userId = ${result.data}")
                    _verificationCode.value = getRandomString(6).uppercase()
                    sendVerificationCode(flow = _eventFlow, userId = result.data, code = _verificationCode.value)
/*                    _eventFlow.emit(
                       UiEvent.ShowSnackbarAndNavigate(
                           uiText = UiText.StringResource(R.string.verification_code_sent),
                           route = Screen.EnterVerificationScreen.route + "?userId=${result.data}&code=${_verificationCode}"
                       )
                    )*/
                    _verificationCode.value = ""
                }
            }
        }
    }

    private fun sendVerificationCode(flow: MutableSharedFlow<UiEvent>, userId: String?, code: String?) {

        _isLoading.value = true

        sendEmail {
            isStartTLSEnabled(true)
            smtp("SMTP")
            smtpUsername("USERNAME")
            smtpPassword("PASSWORD")
            port("587")
            type(MaildroidXType.HTML)
            to(_emailState.value.text)
            from("verifikace@kolejka-app.eu")
            subject("Ověřovací kód pro změnu hesla")
            body("<h4>Váš ověřovací kód pro změnu hesla:</h4><h2>${code}</h2><br><p>---------------------</p><p>Tým Kolejka<p>")
            callback {
                timeOut(100)
                onSuccess {
                    Log.d("MaildroidX", "SUCCESS")
                    println("UserId: $userId, Code: ${verificationCode.value}")
                    viewModelScope.launch{
                        showResult(flow = flow, userId = userId, code = code)
                    }
                    _isLoading.value = false
                }
                onFail {
                    Log.d("MaildroidX", "FAIL")
                    _isLoading.value = false
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

suspend fun showResult(flow: MutableSharedFlow<UiEvent>, userId: String?, code: String?){
    flow.emit(
        UiEvent.ShowSnackbarAndNavigate(
            uiText = UiText.StringResource(R.string.verification_code_sent),
            route = Screen.EnterVerificationScreen.route + "?userId=${userId}&code=${code}"
        )
    )
}