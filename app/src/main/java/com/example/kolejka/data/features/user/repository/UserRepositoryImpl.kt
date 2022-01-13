package com.example.kolejka.data.features.user.repository

import com.example.data.responses.ProfileResponse
import com.example.kolejka.R
import com.example.kolejka.data.features.user.UserApi
import com.example.kolejka.data.util.Resource
import com.example.kolejka.models.User
import com.example.kolejka.view.util.uitext.UiText
import retrofit2.HttpException
import java.io.IOException

class UserRepositoryImpl(
    private val userApi: UserApi
) : UserRepository {
    override suspend fun getUserProfile(): Resource<User> {
        return try {

            val response = userApi.getUserProfile()
            println(response)

            if (response.successful) {
                Resource.Success(response.data)
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
}

