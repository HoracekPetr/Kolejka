package com.example.kolejka.use_cases.user

import com.example.kolejka.data.features.user.repository.UserRepository
import com.example.kolejka.data.util.Resource
import com.example.kolejka.models.User

class GetOtherUserProfileUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userId: String): Resource<User> {
        return repository.getOtherUserProfile(userId)
    }
}