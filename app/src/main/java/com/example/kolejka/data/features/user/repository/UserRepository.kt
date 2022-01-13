package com.example.kolejka.data.features.user.repository

import com.example.data.responses.ProfileResponse
import com.example.kolejka.data.util.Resource
import com.example.kolejka.models.User

interface UserRepository{

    suspend fun getUserProfile(): Resource<User>
}