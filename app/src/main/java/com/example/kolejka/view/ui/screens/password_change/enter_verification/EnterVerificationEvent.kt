package com.example.kolejka.view.ui.screens.password_change.enter_verification

sealed class EnterVerificationEvent{
    data class EnteredCode(val code: String): EnterVerificationEvent()
    object CheckCode: EnterVerificationEvent()
}
