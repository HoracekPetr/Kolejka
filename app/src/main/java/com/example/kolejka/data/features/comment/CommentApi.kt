package com.example.kolejka.data.features.comment

import com.example.data.requests.CreateCommentRequest
import com.example.kolejka.data.response.BasicApiResponse
import com.example.kolejka.models.Comment
import retrofit2.http.*

interface CommentApi {

    @GET("/api/comment/get")
    suspend fun getCommentsForPost(
        @Query("postId") postId: String
    ): BasicApiResponse<List<Comment>>

    @POST("/api/comment/create")
    suspend fun createComment(
        @Body request: CreateCommentRequest
    ): BasicApiResponse<Unit>

    @DELETE("/api/comment/delete")
    suspend fun deleteComment(
        @Query("commentId") commentId: String
    ): BasicApiResponse<Unit>
}