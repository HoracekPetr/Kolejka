package com.example.kolejka.data.features.auth.repository

import android.content.SharedPreferences
import com.example.kolejka.R
import com.example.kolejka.data.features.auth.AuthApi
import com.example.kolejka.data.features.auth.dto.request.LoginAccountRequest
import com.example.kolejka.data.features.auth.dto.request.RegisterAccountRequest
import com.example.kolejka.data.util.Constants.JWT_TOKEN
import com.example.kolejka.data.util.Constants.USER_ID
import com.example.kolejka.data.util.Resource
import com.example.kolejka.data.util.SimpleResource
import com.example.kolejka.view.util.uitext.UiText
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val sharedPreferences: SharedPreferences
) : AuthRepository {
    override suspend fun registerAccount(
        email: String,
        username: String,
        password: String
    ): SimpleResource {

        val request = RegisterAccountRequest(email, username, password)

        return try {
            val response = authApi.registerAccount(request)
            if (response.successful) {
                Resource.Success(Unit)
            } else {
                response.message?.let { msg ->
                    Resource.Error(uiText = UiText.StringDynamic(msg))
                }
                    ?: Resource.Error(uiText = UiText.StringResource(R.string.an_unknown_error_occured))
            }


        } catch (e: IOException) {
            Resource.Error(uiText = UiText.StringResource(R.string.cant_reach_server))
        } catch (e: HttpException) {
            Resource.Error(uiText = UiText.StringResource(R.string.something_went_wrong))
        }
    }

    override suspend fun loginAccount(email: String, password: String): SimpleResource {
        val request = LoginAccountRequest(email, password)

        return try {
            val response = authApi.loginAccount(request)
            if (response.successful) {
                //ULOŽENÍ JWT TOKENU DO SHARED PREFERENCÍ
                sharedPreferences.edit()
                    .putString(JWT_TOKEN, response.data?.token)
                    .apply()
                println("The token: ${response.data?.token}")
                Resource.Success(Unit)

            } else {
                response.message?.let { msg ->
                    Resource.Error(uiText = UiText.StringDynamic(msg))
                }
                    ?: Resource.Error(uiText = UiText.StringResource(R.string.an_unknown_error_occured))
            }


        } catch (e: IOException) {
            Resource.Error(uiText = UiText.StringResource(R.string.cant_reach_server))
        } catch (e: HttpException) {
            Resource.Error(uiText = UiText.StringResource(R.string.something_went_wrong))
        }
    }

    override suspend fun authenticate(): SimpleResource {

        return try {
            authApi.authenticate() //pokud autentikace neselže, vrátíme success, pokud ne, error
            Resource.Success(Unit)
        } catch (e: IOException) {
            Resource.Error(uiText = UiText.StringResource(R.string.cant_reach_server))
        } catch (e: HttpException) {
            Resource.Error(uiText = UiText.StringResource(R.string.something_went_wrong))
        }
    }

    override fun logout() {
        sharedPreferences.edit()
            .remove(JWT_TOKEN)
            .apply()
    }
}
