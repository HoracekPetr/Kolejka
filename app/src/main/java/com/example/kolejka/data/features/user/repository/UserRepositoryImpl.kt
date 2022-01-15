package com.example.kolejka.data.features.user.repository

import android.content.Context
import android.net.Uri
import androidx.core.net.toFile
import com.example.data.requests.CreatePostRequest
import com.example.data.responses.ProfileResponse
import com.example.kolejka.R
import com.example.kolejka.data.features.user.UserApi
import com.example.kolejka.data.features.user.dto.request.UpdateProfileRequest
import com.example.kolejka.data.util.Resource
import com.example.kolejka.data.util.SimpleResource
import com.example.kolejka.data.util.getFileName
import com.example.kolejka.models.User
import com.example.kolejka.view.util.uitext.UiText
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
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

    override suspend fun updateUserProfile(
        username: String,
        bannerR: Float,
        bannerG: Float,
        bannerB: Float,
        profileImageUri: Uri?
    ): SimpleResource {

        val request = UpdateProfileRequest(
            username = username,
            bannerR = bannerR,
            bannerG = bannerG,
            bannerB = bannerB
        )
        val profileImage = profileImageUri?.toFile()

        return try {
            val response = userApi.updateUserProfile(
                updateProfileData = MultipartBody.Part.createFormData(
                    name = "update_profile_data", gson.toJson(request)
                ),
                updateProfileImage = profileImage?.let {
                    MultipartBody.Part.createFormData(
                        name = "update_profile_image",
                        filename = profileImage.name,
                        body = profileImage.asRequestBody()
                    )
                }
            )

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
}
