package com.example.kolejka.view.ui.screens.profile_screen

import com.example.data.responses.ProfileResponse
import com.example.kolejka.models.User

data class ProfileState(
    val profile: User? = null,
    val isLoading: Boolean = false
)