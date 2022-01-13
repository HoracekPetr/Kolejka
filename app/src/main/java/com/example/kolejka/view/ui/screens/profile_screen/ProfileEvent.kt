package com.example.kolejka.view.ui.screens.profile_screen

sealed class ProfileEvent{
    data class GetProfile(val userId: String): ProfileEvent()
}
