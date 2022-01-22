package com.example.kolejka.data.features.post

import com.example.kolejka.data.response.BasicApiResponse
import com.example.kolejka.models.Post
import okhttp3.MultipartBody
import retrofit2.http.*

interface PostApi {

    @GET("/api/post/get")
    suspend fun getPostById(
        @Query("postId") postId: String
    ): BasicApiResponse<Post>

    @GET("/api/post/getPostsByAll")
    suspend fun getPostsByAll(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): List<Post>

    @GET("/api/post/getPostsByCreator")
    suspend fun getPostsByCreator(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): List<Post>

    @GET("/api/post/getPostsWhereMember")
    suspend fun getPostsWhereUserIsMember(
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