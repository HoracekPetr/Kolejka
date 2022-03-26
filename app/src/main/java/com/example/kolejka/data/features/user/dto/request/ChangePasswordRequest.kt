package com.example.kolejka.data.features.user.dto.request

data class ChangePasswordRequest(
    val userId: String?,
    val newPassword: String
)