package com.example.kolejka.use_cases.post

import com.example.kolejka.R
import com.example.kolejka.data.features.post.repository.PostRepository
import com.example.kolejka.data.util.Resource
import com.example.kolejka.data.util.SimpleResource
import com.example.kolejka.view.util.uitext.UiText

class NewPostUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(
        title: String,
        description: String,
        limit: Int?,
        type: Int,
        date: String?,
        location: String?,
        postImageURL: String?
    ): SimpleResource {
        if(title.isBlank() || description.isBlank()){
            return Resource.Error(uiText = UiText.StringResource(R.string.fields_blank))
        }

        if(postImageURL == null){
            return Resource.Error(uiText = UiText.StringResource(R.string.pick_an_image))
        }

        if(limit == null){
            return Resource.Error(uiText = UiText.StringResource(R.string.fields_blank))
        }

        if(limit > 1000){
            return Resource.Error(uiText = UiText.StringResource(R.string.max_limit))
        }

        if(description.length > 200){
            return Resource.Error(uiText = UiText.StringResource(R.string.description_too_long))
        }

        return repository.createNewPost(title, description, limit, type, date, location, postImageURL)
    }
}