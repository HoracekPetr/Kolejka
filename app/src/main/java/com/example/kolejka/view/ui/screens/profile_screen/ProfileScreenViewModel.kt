package com.example.kolejka.view.ui.screens.profile_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.kolejka.data.util.Resource
import com.example.kolejka.models.User
import com.example.kolejka.use_cases.post.GetPostsByCreatorUseCase
import com.example.kolejka.use_cases.post.GetPostsWhereMemberUseCase
import com.example.kolejka.use_cases.user.GetUserProfileUseCase
import com.example.kolejka.view.util.UiEvent
import com.example.kolejka.view.util.uitext.UiText
import com.example.kolejka.view.util.uitext.asString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    getPostsByCreatorUseCase: GetPostsByCreatorUseCase,
    getPostsWhereMemberUseCase: GetPostsWhereMemberUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val postsByCreator = getPostsByCreatorUseCase().cachedIn(viewModelScope)
    val postsWhereMember = getPostsWhereMemberUseCase().cachedIn(viewModelScope)

    private var _state = mutableStateOf(ProfileState())
    var state: State<ProfileState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    private var _showEditProfileDialog = mutableStateOf(false)
    var showEditProfileDialog: State<Boolean> = _showEditProfileDialog

    private var _eventsRadioEnabled = mutableStateOf(true)
    var eventsRadioEnabled: State<Boolean> = _eventsRadioEnabled

    private var _offersRadioEnabled = mutableStateOf(false)
    var offersRadioEnabled: State<Boolean> = _offersRadioEnabled

    init {
        getProfile()
    }


    fun setEditProfileDialogEnabled(enabled: Boolean) {
        _showEditProfileDialog.value = enabled
    }

    fun setYourPostsRadioEnabled(enabled: Boolean) {
        _eventsRadioEnabled.value = enabled
        _offersRadioEnabled.value = !enabled
    }

    fun setJoinedPostsRadioEnabled(enabled: Boolean) {
        _offersRadioEnabled.value = enabled
        _eventsRadioEnabled.value = !enabled
    }

    private fun getProfile() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true
            )

            when (val userResult = getUserProfileUseCase()) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        profile = userResult.data,
                        isLoading = false
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

    fun refreshScreen(){
        viewModelScope.launch{
            _isRefreshing.emit(true)
            getProfile()
            _isRefreshing.emit(false)
        }
    }
}