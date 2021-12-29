package com.example.kolejka.use_cases

import android.util.Patterns
import com.example.kolejka.data.features.auth.models.LoginResult
import com.example.kolejka.data.features.auth.models.RegisterResult
import com.example.kolejka.data.features.auth.repository.AuthRepository
import com.example.kolejka.view.util.errors.Errors

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): LoginResult{
        val emailError = validateEmail(email)
        val passwordError = validatePassword(password)

        if(emailError != null || passwordError != null){
            return LoginResult(
                emailError = emailError,
                passwordError = passwordError
            )
        }

        val result = authRepository.loginAccount(email = email, password = password)

        return LoginResult(
            result = result
        )

    }
}

private fun validateEmail(email: String): Errors? {
    val trimEmail = email.trim()

    if (trimEmail.isBlank()) {
        return Errors.EmptyField
    }

    return null
}

private fun validatePassword(password: String): Errors? {
    val trimPassword = password.trim()

    if(trimPassword.isBlank()){
        return Errors.EmptyField
    }

    return null
}