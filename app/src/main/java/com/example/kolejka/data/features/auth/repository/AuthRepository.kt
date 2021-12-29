package com.example.kolejka.data.features.auth.repository

import com.example.kolejka.data.util.Resource
import com.example.kolejka.data.util.SimpleResource

interface AuthRepository {

    suspend fun registerAccount(
        email: String,
        username: String,
        password: String
    ): SimpleResource

    suspend fun loginAccount(
        email: String,
        password: String
    ): SimpleResource
}