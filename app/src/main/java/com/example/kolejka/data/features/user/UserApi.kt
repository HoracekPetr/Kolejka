package com.example.kolejka.data.features.user

import com.example.data.responses.ProfileResponse
import com.example.kolejka.data.features.user.dto.request.UpdateUserRequest
import com.example.kolejka.data.response.BasicApiResponse
import com.example.kolejka.models.User
import okhttp3.MultipartBody
import retrofit2.http.*

interface UserApi {

    @GET("/api/user/profile")
    suspend fun getUserProfile(
        //@Query("userId") userId: String
    ): BasicApiResponse<User>

    @GET("/api/user/other")
    suspend fun getOtherUserProfile(
        @Query("userId") userId: String
    ): BasicApiResponse<User>

    @PUT("/api/user/update2")
    suspend fun updateUserInfo(
        @Body updateUserRequest: UpdateUserRequest
    ): BasicApiResponse<Unit>
}