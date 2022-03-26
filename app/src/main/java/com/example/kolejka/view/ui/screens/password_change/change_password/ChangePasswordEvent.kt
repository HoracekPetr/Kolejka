package com.example.kolejka.view.ui.screens.password_change.change_password

sealed class ChangePasswordEvent{
    data class EnteredNewPassword(val password: String): ChangePasswordEvent()
    data class EnteredNewPasswordAgain(val passwordAgain: String): ChangePasswordEvent()

    data class ChangePasswordVisibility(val visibility: Boolean): ChangePasswordEvent()
    data class ChangePasswordAgainVisibility(val visibility: Boolean): ChangePasswordEvent()

    object ChangePassword: ChangePasswordEvent()
}
