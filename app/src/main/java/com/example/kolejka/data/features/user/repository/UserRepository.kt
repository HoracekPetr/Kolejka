package com.example.kolejka.data.features.user.repository

import com.example.kolejka.data.response.BasicApiResponse
import com.example.kolejka.data.util.Resource
import com.example.kolejka.data.util.SimpleResource
import com.example.kolejka.models.User

interface UserRepository{

    suspend fun getUserProfile(): Resource<User>

    suspend fun getOtherUserProfile(userId: String): Resource<User>

    suspend fun updateUserInfo(
        username: String,
        bannerR: Float,
        bannerG: Float,
        bannerB: Float,
        profilePictureURL: String?
    ): SimpleResource

    suspend fun getUserId(
        userEmail: String
    ): Resource<String>

    suspend fun changeUserPassword(
        userId: String?,
        newPassword: String
    ): SimpleResource
}