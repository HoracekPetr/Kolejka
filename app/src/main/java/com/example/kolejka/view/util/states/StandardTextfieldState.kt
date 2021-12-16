package com.example.kolejka.view.util.states

import com.example.kolejka.view.util.errors.AuthError

data class StandardTextfieldState(
    val text: String = "",
    val error: AuthError? = null
)
