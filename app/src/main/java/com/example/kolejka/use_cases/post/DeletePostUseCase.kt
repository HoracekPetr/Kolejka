package com.example.kolejka.use_cases.post

import com.example.kolejka.data.features.post.repository.PostRepository
import com.example.kolejka.data.util.SimpleResource

class DeletePostUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(postId: String): SimpleResource{
        return repository.deletePost(postId)
    }
}