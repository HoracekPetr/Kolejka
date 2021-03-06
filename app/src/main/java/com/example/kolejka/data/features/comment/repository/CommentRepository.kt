package com.example.kolejka.data.features.comment.repository

import com.example.kolejka.data.util.Resource
import com.example.kolejka.data.util.SimpleResource
import com.example.kolejka.models.Comment

interface CommentRepository {

    suspend fun getCommentsForPost(postId: String): Resource<List<Comment>>

    suspend fun createComment(comment: String, postId: String): SimpleResource

    suspend fun deleteComment(commentId: String): SimpleResource
}