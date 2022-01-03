package com.example.kolejka.data.features.auth

import com.example.data.responses.BasicApiResponse
import com.example.kolejka.data.features.auth.dto.request.LoginAccountRequest
import com.example.kolejka.data.features.auth.dto.request.RegisterAccountRequest
import com.example.kolejka.data.features.auth.dto.response.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/user/create")
    suspend fun registerAccount(@Body request: RegisterAccountRequest): BasicApiResponse<Unit>

    @POST("/api/user/login")
    suspend fun loginAccount(@Body request: LoginAccountRequest): BasicApiResponse<AuthResponse>

    @GET("/api/user/authenticate")
    suspend fun authenticate()
}