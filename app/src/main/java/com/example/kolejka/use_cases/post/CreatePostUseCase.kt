package com.example.kolejka.use_cases.post

import android.net.Uri
import com.example.kolejka.data.features.post.repository.PostRepository
import com.example.kolejka.data.util.SimpleResource
import java.io.File

class CreatePostUseCase(
    private val postRepository: PostRepository
) {

    suspend operator fun invoke(
        title: String,
        description: String,
        limit: Int?,
        type: Int,
        imageUri: Uri
    ): SimpleResource{
        return postRepository.createPost(title, description, limit, type, imageUri)
    }

}