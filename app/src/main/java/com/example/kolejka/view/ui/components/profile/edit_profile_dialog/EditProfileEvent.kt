package com.example.kolejka.view.ui.components.profile.edit_profile_dialog

import android.net.Uri
import com.example.kolejka.view.ui.screens.new_post_screen.NewPostEvent

sealed class EditProfileEvent{
    data class EnteredUsername(val username: String): EditProfileEvent()
    data class ChangedR(val r: Float): EditProfileEvent()
    data class ChangedG(val g: Float): EditProfileEvent()
    data class ChangedB(val b: Float): EditProfileEvent()
    data class PickedImage(val uri: Uri?): EditProfileEvent()
    data class CropImage(val uri: Uri?): EditProfileEvent()
    object UpdateProfile: EditProfileEvent()
}
