package com.example.kolejka.use_cases.comment

import com.example.kolejka.data.features.comment.repository.CommentRepository
import com.example.kolejka.data.util.SimpleResource

class DeleteCommentUseCase(
    private val repository: CommentRepository
) {
    suspend operator fun invoke(commentId: String): SimpleResource{
        return repository.deleteComment(commentId)
    }
}