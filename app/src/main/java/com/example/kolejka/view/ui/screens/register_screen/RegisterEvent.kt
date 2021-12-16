package com.example.kolejka.view.ui.screens.register_screen

sealed class RegisterEvent{
    data class EnteredUsername(val username: String) : RegisterEvent()
    data class EnteredEmail(val email: String): RegisterEvent()
    data class EnteredPassword(val password: String): RegisterEvent()
    data class ChangePasswordVisibility(val visibility: Boolean): RegisterEvent()
    object Register: RegisterEvent()
}
