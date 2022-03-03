package com.example.kolejka.data.features.post

import com.example.kolejka.data.features.post.dto.request.AddMemberRequest
import com.example.kolejka.data.features.post.dto.request.NewPostRequest
import com.example.kolejka.data.features.post.dto.response.PostDetailResponse
import com.example.kolejka.data.response.BasicApiResponse
import com.example.kolejka.models.Post
import okhttp3.MultipartBody
import retrofit2.http.*

interface PostApi {

    @GET("/api/post/get")
    suspend fun getPostById(
        @Query("postId") postId: String
    ): BasicApiResponse<PostDetailResponse>

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


    @POST("api/post/new")
    suspend fun createNewPost(
        @Body request: NewPostRequest
    ): BasicApiResponse<Unit>

    @POST("/api/post/addPostMember")
    suspend fun addPostMember(
        @Body request: AddMemberRequest
    ): BasicApiResponse<Unit>

    @DELETE("/api/post/delete")
    suspend fun deletePost(
        @Query("postId") postId: String
    ): BasicApiResponse<Unit>
}