package com.example.kolejka.view.ui.screens.login_screen

import com.example.kolejka.view.ui.screens.register_screen.RegisterEvent

sealed class LoginEvent{
    data class EnteredEmail(val email: String): LoginEvent()
    data class EnteredPassword(val password: String): LoginEvent()
    data class ChangePasswordVisibility(val visibility: Boolean): LoginEvent()
    object Login: LoginEvent()
}
