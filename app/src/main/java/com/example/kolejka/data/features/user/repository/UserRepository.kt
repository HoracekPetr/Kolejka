package com.example.kolejka.data.features.user.repository

import android.net.Uri
import com.example.data.responses.ProfileResponse
import com.example.kolejka.data.features.user.dto.request.UpdateProfileRequest
import com.example.kolejka.data.util.Resource
import com.example.kolejka.data.util.SimpleResource
import com.example.kolejka.models.User

interface UserRepository{

    suspend fun getUserProfile(): Resource<User>

    suspend fun updateUserProfile(
        username: String,
        bannerR: Float,
        bannerG: Float,
        bannerB: Float,
        profileImageUri: Uri?
    ): SimpleResource
}