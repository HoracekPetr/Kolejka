package com.example.kolejka.data.response

data class BasicApiResponse<T>(
    val message: String? = null,
    val successful: Boolean,
    val data: T? = null
)