package com.example.kolejka.data.features.user

import com.example.data.responses.ProfileResponse
import com.example.kolejka.data.response.BasicApiResponse
import com.example.kolejka.models.User
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("/api/user/profile")
    suspend fun getUserProfile(
        //@Query("userId") userId: String
    ): BasicApiResponse<User>
}