package com.example.kolejka.data.features.post.repository

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.core.net.toFile
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.requests.CreatePostRequest
import com.example.kolejka.R
import com.example.kolejka.data.features.auth.dto.request.RegisterAccountRequest
import com.example.kolejka.data.features.post.PostApi
import com.example.kolejka.data.features.post.paging.PostSource
import com.example.kolejka.data.util.Constants
import com.example.kolejka.data.util.Resource
import com.example.kolejka.data.util.SimpleResource
import com.example.kolejka.data.util.getFileName
import com.example.kolejka.models.Post
import com.example.kolejka.view.util.uitext.UiText
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class PostRepositoryImpl(
    private val postApi: PostApi,
    private val gson: Gson,
    private val appContext: Context
) : PostRepository {

    override val posts: Flow<PagingData<Post>>
        get() = Pager(PagingConfig(pageSize = Constants.POSTS_PAGE_SIZE)) {
            PostSource(postApi)
        }.flow

    override suspend fun createPost(
        title: String,
        description: String,
        limit: Int?,
        type: Int,
        imageUri: Uri
    ): SimpleResource {

        val request = CreatePostRequest(title, description, limit ?: 0, type)

        val imageFile = withContext(Dispatchers.IO) {
            appContext.contentResolver.openFileDescriptor(imageUri, "r")?.let{ fd ->
                val inputStream = FileInputStream(fd.fileDescriptor)
                val file = File(
                    appContext.cacheDir,
                    appContext.contentResolver.getFileName(imageUri)
                )
                val outputStream = FileOutputStream(file)
                inputStream.copyTo(outputStream)
                file
            }

        } ?: return Resource.Error(uiText = UiText.StringResource(R.string.file_not_found))

        return try {

            val response = postApi.createPost(
                createPostData = MultipartBody.Part
                    .createFormData("create_post_data", gson.toJson(request)),
                createPostImage = MultipartBody.Part
                    .createFormData(
                        name = "create_post_image",
                        filename = imageFile.name,
                        imageFile.asRequestBody()
                    )
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