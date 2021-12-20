package com.example.kolejka.use_cases

import android.util.Patterns
import com.example.kolejka.data.features.auth.RegisterResult
import com.example.kolejka.data.features.auth.repository.AuthRepository
import com.example.kolejka.data.util.SimpleResource
import com.example.kolejka.view.util.Constants
import com.example.kolejka.view.util.errors.Errors

class RegisterUseCase(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        email: String,
        username: String,
        password: String
    ): RegisterResult {

        val emailError = validateEmail(email)
        val usernameError = validateUsername(username)
        val passwordError = validatePassword(password)

        if(emailError != null || usernameError != null || passwordError != null){
            return RegisterResult(
                emailError = emailError,
                usernameError = usernameError,
                passwordError = passwordError,
            )
        }

        val result = authRepository.registerAccount(email, username, password)

        return RegisterResult(
            result = result
        )
    }
}

private fun validateEmail(email: String): Errors? {
    val trimEmail = email.trim()

    if (!Patterns.EMAIL_ADDRESS.matcher(trimEmail).matches()) {
        return Errors.InvalidEmail
    }

    if (trimEmail.isBlank()) {
        return Errors.EmptyField
    }

    return null
}

private fun validateUsername(username: String): Errors?{
    val trimUsername = username.trim()
    if(trimUsername.isBlank()){
        return Errors.EmptyField
    }

    if(trimUsername.length < Constants.MIN_USERNAME_LENGTH){
        return Errors.InputTooShort
    }

    return null
}

private fun validatePassword(password: String): Errors?{
    val trimPassword = password.trim()
    if(trimPassword.isBlank()){
        return Errors.EmptyField
    }

    if(trimPassword.length < Constants.MIN_PASSWORD_LENGTH){
        return Errors.InputTooShort
    }

    if(!trimPassword.contains(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]+\$"))){
        return Errors.InvalidPassword
    }

    return null
}