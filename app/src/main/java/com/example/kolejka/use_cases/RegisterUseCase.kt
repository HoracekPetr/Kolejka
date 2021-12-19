package com.example.kolejka.use_cases

import com.example.kolejka.data.features.auth.repository.AuthRepository
import com.example.kolejka.data.util.SimpleResource

class RegisterUseCase(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        email: String,
        username: String,
        password: String
    ): SimpleResource {
        return authRepository.registerAccount(email.trim(), username.trim(), password.trim())
    }
}