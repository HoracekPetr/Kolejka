package com.example.kolejka.view.ui.screens.password_change.enter_email

sealed class EnterEmailEvent{
    data class EnteredEmail(val email: String): EnterEmailEvent()
    object CheckEmail: EnterEmailEvent()
}
