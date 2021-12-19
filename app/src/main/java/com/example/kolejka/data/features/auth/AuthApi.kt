package com.example.kolejka.data.features.auth

import com.example.data.responses.BasicApiResponse
import com.example.kolejka.data.features.auth.dto.request.RegisterAccountRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/user/create")
    suspend fun registerUser(@Body request: RegisterAccountRequest): BasicApiResponse

}