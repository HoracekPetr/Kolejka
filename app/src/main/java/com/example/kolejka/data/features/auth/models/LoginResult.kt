package com.example.kolejka.data.features.auth.models

import com.example.kolejka.data.util.SimpleResource
import com.example.kolejka.view.util.errors.Errors

data class LoginResult(
    val emailError: Errors? = null,
    val passwordError: Errors? = null,
    val result: SimpleResource? = null
)
