package com.example.kolejka.view.ui.components.profile.edit_profile_dialog

import com.example.kolejka.models.User

data class EditProfileState(
    val isLoading: Boolean = false,
    val username: String = "",
    val profileImageUrl: String? = "",
    val bannerR: Float = 0f,
    val bannerG: Float = 0f,
    val bannerB: Float = 0f
)