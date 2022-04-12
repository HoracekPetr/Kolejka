package com.example.kolejka.view.ui.screens.edit_post_screen

import android.net.Uri
import com.example.kolejka.view.ui.screens.new_post_screen.NewPostEvent

sealed class EditPostEvent{
    data class EnteredTitle(val title: String): EditPostEvent()
    data class EnteredDescription(val description: String): EditPostEvent()
    data class EnteredLocation(val location: String): EditPostEvent()
    data class EnteredLimit(val limit: String): EditPostEvent()
    data class PickedImage(val uri: Uri?): EditPostEvent()
    data class CropImage(val uri: Uri?): EditPostEvent()
    data class CalendarEnabled(val enabled: Boolean): EditPostEvent()
    data class SelectDate(val date: String): EditPostEvent()
    object UpdatePost: EditPostEvent()
}
