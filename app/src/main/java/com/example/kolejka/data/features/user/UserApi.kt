package com.example.kolejka.data.features.user

import com.example.data.responses.ProfileResponse
import com.example.kolejka.data.response.BasicApiResponse
import com.example.kolejka.models.User
import okhttp3.MultipartBody
import retrofit2.http.*

interface UserApi {

    @GET("/api/user/profile")
    suspend fun getUserProfile(
        //@Query("userId") userId: String
    ): BasicApiResponse<User>

    @Multipart
    @PUT("/api/user/update")
    suspend fun updateUserProfile(
        @Part updateProfileData: MultipartBody.Part,
        @Part updateProfileImage: MultipartBody.Part?
    ): BasicApiResponse<Unit>
}