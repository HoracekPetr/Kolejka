package com.example.kolejka.view.util.states

import com.example.kolejka.view.util.errors.Errors

data class StandardTextfieldState(
    val text: String = "",
    val error: Errors? = null
)
