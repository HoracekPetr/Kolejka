package com.example.kolejka.data.features.post.repository

import android.content.Context
import android.net.Uri
import androidx.core.net.toFile
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.requests.CreatePostRequest
import com.example.kolejka.R
import com.example.kolejka.data.features.post.PostApi
import com.example.kolejka.data.features.post.paging.AllPostsSource
import com.example.kolejka.data.features.post.paging.CreatorPostsSource
import com.example.kolejka.data.features.post.paging.MemberPostsSource
import com.example.kolejka.data.util.Constants
import com.example.kolejka.data.util.Resource
import com.example.kolejka.data.util.SimpleResource
import com.example.kolejka.data.util.getFileName
import com.example.kolejka.models.Post
import com.example.kolejka.view.util.uitext.UiText
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class PostRepositoryImpl(
    private val postApi: PostApi,
    private val gson: Gson
) : PostRepository {

    override val posts: Flow<PagingData<Post>>
        get() = Pager(PagingConfig(pageSize = Constants.POSTS_PAGE_SIZE)) {
            AllPostsSource(postApi)
        }.flow

    override val postsByCreator: Flow<PagingData<Post>>
        get() = Pager(PagingConfig(pageSize = Constants.POSTS_PAGE_SIZE)){
            CreatorPostsSource(postApi)
        }.flow

    override val postsWhereMember: Flow<PagingData<Post>>
        get() = Pager(PagingConfig(pageSize = Constants.POSTS_PAGE_SIZE)){
            MemberPostsSource(postApi)
        }.flow

    override suspend fun createPost(
        title: String,
        description: String,
        limit: Int?,
        type: Int,
        imageUri: Uri
    ): SimpleResource {

        val request = CreatePostRequest(title, description, limit ?: 0, type)
        val imageFile = imageUri.toFile()

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