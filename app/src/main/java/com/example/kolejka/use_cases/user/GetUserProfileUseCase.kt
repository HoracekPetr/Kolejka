package com.example.kolejka.use_cases.user

import com.example.data.responses.ProfileResponse
import com.example.kolejka.data.features.user.repository.UserRepository
import com.example.kolejka.data.util.Resource
import com.example.kolejka.models.User

class GetUserProfileUseCase(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): Resource<User> {
        return userRepository.getUserProfile()
    }

}