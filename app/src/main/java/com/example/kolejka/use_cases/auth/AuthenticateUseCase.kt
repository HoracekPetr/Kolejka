package com.example.kolejka.use_cases.auth

import com.example.kolejka.data.features.auth.repository.AuthRepository
import com.example.kolejka.data.util.SimpleResource

class AuthenticateUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): SimpleResource{
        return repository.authenticate()
    }
}