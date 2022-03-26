package com.example.kolejka.data.features.user.models

import com.example.kolejka.data.util.SimpleResource
import com.example.kolejka.view.util.errors.Errors

data class ChangePasswordResult(
    val newPasswordError: Errors? = null,
    val newPasswordAgainError: Errors? = null,
    val changePasswordResult: SimpleResource? = null,
    val passwordNotMatching: Boolean = false
)
