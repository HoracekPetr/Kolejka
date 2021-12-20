package com.example.kolejka.data.features.auth

import com.example.kolejka.data.util.SimpleResource
import com.example.kolejka.view.util.errors.Errors

data class RegisterResult(
    val emailError: Errors? = null,
    val usernameError: Errors? = null,
    val passwordError: Errors? = null,
    val result: SimpleResource? = null
)
