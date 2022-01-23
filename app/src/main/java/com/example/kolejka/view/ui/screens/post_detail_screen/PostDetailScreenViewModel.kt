package com.example.kolejka.view.ui.screens.post_detail_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kolejka.data.util.Resource
import com.example.kolejka.use_cases.comment.GetCommentsForPostUseCase
import com.example.kolejka.use_cases.post.GetPostByIdUseCase
import com.example.kolejka.view.util.UiEvent
import com.example.kolejka.view.util.uitext.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailScreenViewModel @Inject constructor(
    private val getPostByIdUseCase: GetPostByIdUseCase,
    private val getCommentsForPostUseCase: GetCommentsForPostUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _availability = mutableStateOf(0)
    val availability: State<Int> = _availability

    private val _commentText = mutableStateOf("")
    val commentText: State<String> = _commentText

    private val _members = mutableStateOf(emptyList<String>())
    val members: State<List<String>> = _members

    private val _state = mutableStateOf(PostDetailState())
    val state: State<PostDetailState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>("postId")?.let{ postId ->
            getPostById(postId)
            getCommentsForPost(postId)
        }
    }

    fun incrementAvailability(){
        _availability.value++
    }

    fun setCommentText(comment: String){
        _commentText.value = comment
    }

    private fun getPostById(postId: String){
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true
            )

            when(val postResult = getPostByIdUseCase(postId)){
                is Resource.Error -> {

                    _state.value = _state.value.copy(
                        isLoading = false
                    )

                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = postResult.uiText ?: UiText.unknownError()
                        )
                    )
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        post = postResult.data
                    )
                }
            }
        }
    }

    private fun getCommentsForPost(postId: String){
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true
            )

            when(val commentsResult = getCommentsForPostUseCase(postId)){
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )

                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = commentsResult.uiText ?: UiText.unknownError()
                        )
                    )
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        comments = commentsResult.data
                    )
                }
            }
        }
    }

}