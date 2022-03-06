package com.example.kolejka.view.ui.screens.profile_screen.other_user_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.kolejka.data.util.Resource
import com.example.kolejka.models.Post
import com.example.kolejka.use_cases.post.GetPostsByOtherCreatorUseCase
import com.example.kolejka.use_cases.user.GetOtherUserProfileUseCase
import com.example.kolejka.view.util.UiEvent
import com.example.kolejka.view.util.uitext.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtherUserScreenViewModel @Inject constructor(
    private val getOtherUserProfileUseCase: GetOtherUserProfileUseCase,
    private val getPostsByOtherCreatorUseCase: GetPostsByOtherCreatorUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private var _state = mutableStateOf(OtherProfileState())
    var state: State<OtherProfileState> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    init {
        savedStateHandle.get<String>("userId")?.let { id ->
            getProfile(id)
            getPosts(userId = id)
        }
    }


    private fun getProfile(userId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true
            )

            when (val userResult = getOtherUserProfileUseCase(userId)) {
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

    private fun getPosts(userId: String){
        viewModelScope.launch{
            _state.value = _state.value.copy(
                posts = getPostsByOtherCreatorUseCase(userId).cachedIn(this)
            )
        }
    }

    fun refreshScreen(userId: String){
        viewModelScope.launch{
            _isRefreshing.emit(true)
            getProfile(userId)
            _isRefreshing.emit(false)
        }
    }


}