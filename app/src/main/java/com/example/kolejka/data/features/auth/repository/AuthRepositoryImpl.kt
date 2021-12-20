package com.example.kolejka.data.features.auth.repository

import com.example.kolejka.R
import com.example.kolejka.data.features.auth.AuthApi
import com.example.kolejka.data.features.auth.dto.request.RegisterAccountRequest
import com.example.kolejka.data.util.Resource
import com.example.kolejka.data.util.SimpleResource
import com.example.kolejka.view.util.uitext.UiText
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun registerAccount(
        email: String,
        username: String,
        password: String
    ): SimpleResource {

        val request = RegisterAccountRequest(email, username, password)

        try {
            val response = authApi.registerUser(request)
            if (response.successful) {
                return Resource.Success(Unit)
            } else {
                response.message?.let { msg ->
                    return Resource.Error(uiText = UiText.StringDynamic(msg))
                } ?: return Resource.Error(uiText = UiText.StringResource(R.string.an_unknown_error_occured))
            }


        } catch (e: IOException) {
            return Resource.Error(uiText = UiText.StringResource(R.string.cant_reach_server))
        } catch (e: HttpException) {
            return Resource.Error(uiText = UiText.StringResource(R.string.something_went_wrong))
        }
    }
}