package com.example.kolejka.use_cases.auth

import com.example.kolejka.data.features.auth.repository.AuthRepository
import com.example.kolejka.data.features.user.repository.UserRepository

class LogoutUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke(){
        repository.logout()
    }
}