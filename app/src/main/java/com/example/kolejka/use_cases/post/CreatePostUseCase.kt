package com.example.kolejka.use_cases.post

import android.net.Uri
import com.example.kolejka.R
import com.example.kolejka.data.features.post.repository.PostRepository
import com.example.kolejka.data.util.Resource
import com.example.kolejka.data.util.SimpleResource
import com.example.kolejka.view.util.uitext.UiText
import java.io.File

class CreatePostUseCase(
    private val postRepository: PostRepository
) {

    suspend operator fun invoke(
        title: String,
        description: String,
        limit: Int?,
        type: Int,
        imageUri: Uri?,
        date: String,
        location: String
    ): SimpleResource{
        if(title.isBlank() || description.isBlank()){
            return Resource.Error(uiText = UiText.StringResource(R.string.fields_blank))
        }

        if(imageUri == null){
            return Resource.Error(uiText = UiText.StringResource(R.string.pick_an_image))
        }

        if(limit == null){
            return Resource.Error(uiText = UiText.StringResource(R.string.fields_blank))
        }

        if(description.length > 1000){
            return Resource.Error(uiText = UiText.StringResource(R.string.description_too_long))
        }

        return postRepository.createPost(title, description, limit, type, imageUri, date, location)
    }

}