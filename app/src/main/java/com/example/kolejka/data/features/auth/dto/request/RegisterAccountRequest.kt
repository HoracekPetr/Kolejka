package com.example.kolejka.data.features.auth.dto.request

data class RegisterAccountRequest(
    val email: String,
    val username: String,
    val password: String
)
