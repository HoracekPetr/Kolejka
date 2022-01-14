package com.example.kolejka.view.ui.components.profile.edit_profile_dialog

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kolejka.R
import com.example.kolejka.data.util.Resource
import com.example.kolejka.use_cases.user.GetUserProfileUseCase
import com.example.kolejka.use_cases.user.UpdateProfileUseCase
import com.example.kolejka.view.util.UiEvent
import com.example.kolejka.view.util.uitext.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileDialogViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
): ViewModel(){

    private val _state = mutableStateOf(EditProfileState())
    val state: State<EditProfileState> = _state

    private val _profileImageUri = mutableStateOf<Uri?>(null)
    val profileImageUri: State<Uri?> = _profileImageUri

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

/*    private val _username = mutableStateOf("")
    val username: State<String> = _username

    fun setUsername(username:String){
        _username.value = username
    }

    private val _r = mutableStateOf(0f)
    val r: State<Float> = _r

    private val _g = mutableStateOf(0f)
    val g: State<Float> = _g

    private val _b = mutableStateOf(0f)
    val b: State<Float> = _b

    fun setR(r: Float){
        _r.value = r
    }

    fun setG(g: Float){
        _g.value = g
    }

    fun setB(b: Float){
        _b.value = b
    }*/

    init {
        getProfile()
    }

    fun onEvent(event: EditProfileEvent){
        when(event){
            is EditProfileEvent.EnteredUsername -> {
                _state.value = _state.value.copy(
                    username = event.username
                )
            }
            is EditProfileEvent.ChangedR -> {
                _state.value = _state.value.copy(
                    bannerR = event.r
                )
            }
            is EditProfileEvent.ChangedG -> {
                _state.value = _state.value.copy(
                    bannerG = event.g
                )
            }
            is EditProfileEvent.ChangedB -> {
                _state.value = _state.value.copy(
                    bannerB = event.b
                )
            }
            is EditProfileEvent.PickedImage -> {
                _profileImageUri.value = event.uri
            }
            is EditProfileEvent.UpdateProfile -> {
                updateProfile()
            }
        }
    }

    private fun updateProfile(){
        viewModelScope.launch {
            val result = updateProfileUseCase(
                username = _state.value.username,
                bannerR = _state.value.bannerR,
                bannerG = _state.value.bannerB,
                bannerB = _state.value.bannerB,
                profileImageUri = _profileImageUri.value
            )

            when(result){
                is Resource.Success -> {
                    _eventFlow.emit(UiEvent.ShowSnackbar(
                        uiText = UiText.StringResource(R.string.user_updated)
                    ))
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnackbar(result.uiText ?: UiText.unknownError()
                    ))
                }
            }
        }
    }

    private fun getProfile() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true
            )

            when (val  userResult = getUserProfileUseCase()) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        username = userResult.data?.username ?: "",
                        profileImageUrl = userResult.data?.profilePictureUrl ?: "",
                        bannerR = userResult.data?.bannerR ?: 0f,
                        bannerG = userResult.data?.bannerG ?: 0f,
                        bannerB = userResult.data?.bannerB ?: 0f
                    )
                    println(userResult.data)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )

                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = userResult.uiText ?: UiText.unknownError()
                        )
                    )
                }
            }
        }
    }
}