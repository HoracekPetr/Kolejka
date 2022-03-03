package com.example.kolejka.data.features.user.repository

import android.content.Context
import com.example.kolejka.R
import com.example.kolejka.data.features.user.UserApi
import com.example.kolejka.data.features.user.dto.request.UpdateUserRequest
import com.example.kolejka.data.util.Resource
import com.example.kolejka.data.util.SimpleResource
import com.example.kolejka.models.User
import com.example.kolejka.view.util.uitext.UiText
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

class UserRepositoryImpl(
    private val userApi: UserApi,
    private val gson: Gson,
    private val appContext: Context
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

    override suspend fun updateUserInfo(
        username: String,
        bannerR: Float,
        bannerG: Float,
        bannerB: Float,
        profilePictureURL: String?
    ): SimpleResource {
        val request = UpdateUserRequest(
            username = username,
            bannerR = bannerR,
            bannerG = bannerG,
            bannerB = bannerB,
            profilePictureURL = profilePictureURL
        )

        return try {
            val response = userApi.updateUserInfo(request)

            if(response.successful){
                Resource.Success(Unit)
            } else {
                response.message?.let { msg ->
                    Resource.Error(uiText = UiText.StringDynamic(msg))
                } ?: Resource.Error(uiText = UiText.StringResource(R.string.an_unknown_error_occured))
            }
        } catch (e: IOException) {
            Resource.Error(uiText = UiText.StringResource(R.string.cant_reach_server))
        } catch (e: HttpException) {
            Resource.Error(uiText = UiText.StringResource(R.string.something_went_wrong))
        }
    }
}
