package com.example.kolejka.data.features.post.repository

import android.net.Uri
import androidx.core.net.toFile
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.requests.CreatePostRequest
import com.example.kolejka.R
import com.example.kolejka.data.features.post.PostApi
import com.example.kolejka.data.features.post.dto.request.AddMemberRequest
import com.example.kolejka.data.features.post.dto.request.NewPostRequest
import com.example.kolejka.data.features.post.dto.response.PostDetailResponse
import com.example.kolejka.data.features.post.paging.AllPostsSource
import com.example.kolejka.data.features.post.paging.CreatorPostsSource
import com.example.kolejka.data.features.post.paging.MemberPostsSource
import com.example.kolejka.data.util.Constants
import com.example.kolejka.data.util.Resource
import com.example.kolejka.data.util.SimpleResource
import com.example.kolejka.models.Post
import com.example.kolejka.view.util.uitext.UiText
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.IOException

class PostRepositoryImpl(
    private val postApi: PostApi,
    private val gson: Gson
) : PostRepository {

    override val posts: Flow<PagingData<Post>>
        get() = Pager(PagingConfig(pageSize = Constants.DEFAULT_PAGE_SIZE)) {
            AllPostsSource(postApi)
        }.flow

    override val postsByCreator: Flow<PagingData<Post>>
        get() = Pager(PagingConfig(pageSize = Constants.DEFAULT_PAGE_SIZE)){
            CreatorPostsSource(postApi)
        }.flow

    override val postsWhereMember: Flow<PagingData<Post>>
        get() = Pager(PagingConfig(pageSize = Constants.DEFAULT_PAGE_SIZE)){
            MemberPostsSource(postApi)
        }.flow

    override suspend fun getPostById(postId: String): Resource<PostDetailResponse> {
        return try {

            val response = postApi.getPostById(postId)
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

    override suspend fun createNewPost(
        title: String,
        description: String,
        limit: Int?,
        type: Int,
        date: String?,
        location: String?,
        postImageURL: String?
    ): SimpleResource {
        val request = NewPostRequest(
            title,
            description,
            limit ?: 0,
            type,
            date ?: "",
            location ?: "",
            postImageURL ?: ""
        )

        return try {
            val response = postApi.createNewPost(request)

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

    override suspend fun addPostMember(postId: String): SimpleResource {

        val request = AddMemberRequest(postId)

        return try {
            val response = postApi.addPostMember(request)
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

    override suspend fun deletePost(postId: String): SimpleResource {
        return try {
            val response = postApi.deletePost(postId)
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