package com.example.kolejka.use_cases.comment

import com.example.kolejka.data.features.comment.repository.CommentRepository
import com.example.kolejka.data.util.Resource
import com.example.kolejka.models.Comment

class GetCommentsForPostUseCase(
    private val commentRepository: CommentRepository
) {
    suspend operator fun invoke(postId: String): Resource<List<Comment>>{
        return commentRepository.getCommentsForPost(postId)
    }
}