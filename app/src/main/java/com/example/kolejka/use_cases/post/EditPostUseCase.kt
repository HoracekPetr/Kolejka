package com.example.kolejka.use_cases.post

import com.example.kolejka.data.features.post.repository.PostRepository
import com.example.kolejka.data.util.SimpleResource

class EditPostUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(
        postId: String,
        title: String,
        description: String,
        limit: Int?,
        date: String,
        location: String,
        postImageUrl: String?,
        price: Int?
    ): SimpleResource {
        return repository.editPostInfo(
            postId = postId,
            title = title,
            description = description,
            limit = limit,
            date = date,
            location = location,
            postImageUrl = postImageUrl,
            price = price
        )
    }
}