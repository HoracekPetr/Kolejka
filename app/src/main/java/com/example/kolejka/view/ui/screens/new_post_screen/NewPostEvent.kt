package com.example.kolejka.view.ui.screens.new_post_screen

import android.net.Uri

sealed class NewPostEvent{
    data class EnteredTitle(val title: String): NewPostEvent()
    data class EnteredDescription(val description: String): NewPostEvent()
    data class EnteredLimit(val limit: String): NewPostEvent()
    data class PickedImage(val uri: Uri?): NewPostEvent()
    object EventPicked : NewPostEvent()
    object OfferPicked: NewPostEvent()
    object CreatePost: NewPostEvent()
}
