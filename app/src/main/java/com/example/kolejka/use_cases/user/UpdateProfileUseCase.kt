package com.example.kolejka.use_cases.user

import android.net.Uri
import com.example.kolejka.R
import com.example.kolejka.data.features.auth.repository.AuthRepository
import com.example.kolejka.data.features.user.repository.UserRepository
import com.example.kolejka.data.util.Resource
import com.example.kolejka.data.util.SimpleResource
import com.example.kolejka.view.util.Constants
import com.example.kolejka.view.util.uitext.UiText

class UpdateProfileUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(
        username: String,
        bannerR: Float,
        bannerG: Float,
        bannerB: Float,
        profilePictureUrl: String?
    ): SimpleResource{
        if(username.isBlank()){
            return Resource.Error(
                uiText = UiText.StringResource(R.string.username_cant_be_empty)
            )
        }
        if(username.length < 3){
            return Resource.Error(
                uiText = UiText.StringResource(R.string.username_too_short2)
            )
        }

        return repository.updateUserInfo(
            username = username,
            bannerR = bannerR,
            bannerG = bannerG,
            bannerB = bannerB,
            profilePictureURL = profilePictureUrl
        )
    }
}