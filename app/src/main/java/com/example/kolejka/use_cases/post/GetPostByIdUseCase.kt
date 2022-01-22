package com.example.kolejka.use_cases.post

import com.example.kolejka.data.features.post.repository.PostRepository
import com.example.kolejka.data.util.Resource
import com.example.kolejka.models.Post

class GetPostByIdUseCase(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(postId: String): Resource<Post>{
        return postRepository.getPostById(postId)
    }
}