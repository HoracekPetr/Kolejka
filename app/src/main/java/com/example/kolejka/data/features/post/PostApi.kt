package com.example.kolejka.data.features.post

import com.example.data.requests.CreatePostRequest
import com.example.data.responses.BasicApiResponse
import com.example.kolejka.models.Post
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface PostApi {

    @GET("/api/post/getPostsByAll")
    suspend fun getPostsByAll(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): List<Post>

    @Multipart
    @POST("/api/post/create")
    suspend fun createPost(
        @Part createPostData: MultipartBody.Part,
        @Part createPostImage: MultipartBody.Part
    ): BasicApiResponse<Unit>
}