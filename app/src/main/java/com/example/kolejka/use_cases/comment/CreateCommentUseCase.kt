package com.example.kolejka.use_cases.comment

import com.example.kolejka.data.features.comment.repository.CommentRepository
import com.example.kolejka.data.util.SimpleResource

class CreateCommentUseCase(
    private val commentRepository: CommentRepository
) {

    suspend operator fun invoke(comment: String, postId: String): SimpleResource{
        return commentRepository.createComment(comment, postId)
    }
}