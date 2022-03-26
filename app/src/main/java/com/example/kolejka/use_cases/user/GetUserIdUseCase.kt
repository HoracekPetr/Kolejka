package com.example.kolejka.use_cases.user

import com.example.kolejka.data.features.user.repository.UserRepository
import com.example.kolejka.data.util.Resource

class GetUserIdUseCase(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(userEmail: String): Resource<String> {
        return userRepository.getUserId(userEmail)
    }

}