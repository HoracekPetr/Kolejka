package com.example.kolejka.view.ui.screens.post_detail_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kolejka.data.util.Resource
import com.example.kolejka.use_cases.comment.CreateCommentUseCase
import com.example.kolejka.use_cases.comment.DeleteCommentUseCase
import com.example.kolejka.use_cases.comment.GetCommentsForPostUseCase
import com.example.kolejka.use_cases.post.AddPostMemberUseCase
import com.example.kolejka.use_cases.post.DeletePostUseCase
import com.example.kolejka.use_cases.post.GetPostByIdUseCase
import com.example.kolejka.view.util.Screen
import com.example.kolejka.view.util.UiEvent
import com.example.kolejka.view.util.states.StandardTextfieldState
import com.example.kolejka.view.util.uitext.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailScreenViewModel @Inject constructor(
    private val getPostByIdUseCase: GetPostByIdUseCase,
    private val getCommentsForPostUseCase: GetCommentsForPostUseCase,
    private val createCommentUseCase: CreateCommentUseCase,
    private val addPostMemberUseCase: AddPostMemberUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private var _availability = mutableStateOf(0)
    var availability: State<Int> = _availability

    private val _commentState = mutableStateOf(StandardTextfieldState())
    val commentState: State<StandardTextfieldState> = _commentState

    private val _members = mutableStateOf(emptyList<String>())
    val members: State<List<String>> = _members

    private val _state = mutableStateOf(PostDetailState())
    val state: State<PostDetailState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    init {
        savedStateHandle.get<String>("postId")?.let{ postId ->
            getPostById(postId)
            getCommentsForPost(postId)
            refreshScreen(postId)
        }
    }

    fun onEvent(event: PostDetailEvent){
        when(event){
            is PostDetailEvent.Comment -> savedStateHandle.get<String>("postId")?.let{ postId ->
                createComment(postId)
            }
            is PostDetailEvent.AddMember -> savedStateHandle.get<String>("postId")?.let{ postId ->
                addPostMember(postId)
            }
            is PostDetailEvent.DeletePost -> {
                _state.value = _state.value.copy(
                    showDeletePostDialog = true
                )
            }

            is PostDetailEvent.ConfirmPostDelete -> savedStateHandle.get<String>("postId")?.let{ postId ->
                deletePost(postId)
                _state.value = _state.value.copy(
                    showDeletePostDialog = false
                )
            }
            is PostDetailEvent.DismissPostDelete -> {
                _state.value = _state.value.copy(
                    showDeletePostDialog = false
                )
            }
            is PostDetailEvent.DeleteComment -> {
                _state.value = _state.value.copy(
                    showDeleteCommentDialog = true
                )
            }
            is PostDetailEvent.ConfirmCommentDelete -> {
                val postId = savedStateHandle.get<String>("postId")
                event.commentId?.let { commentId ->
                    deleteComment(commentId, postId ?: "") //TADY MOŽNÁ POUPRAVIT, ABY NEMOHLO DOJÍT K TOMU, ŽE SE PO SMAZÁNÍ KOMENTU NENAČTE POST / error
                }
                _state.value = _state.value.copy(
                    showDeleteCommentDialog = false
                )

            }
            is PostDetailEvent.DismissCommentDelete -> {
                _state.value = _state.value.copy(
                    showDeleteCommentDialog = false
                )
            }
            is PostDetailEvent.EditPost -> {
                val postId = savedStateHandle.get<String>("postId")
                if(postId != null){
                    viewModelScope.launch {
                        _eventFlow.emit(
                            UiEvent.Navigate(
                                route = Screen.EditPostScreen.route + "?postId=$postId"
                            )
                        )
                    }
                }
            }
        }
    }

    fun setCommentText(comment: String){
        _commentState.value = _commentState.value.copy(
            text = comment
        )
    }

    private fun getPostById(postId: String){
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isPostLoading = true
            )

            when(val postResult = getPostByIdUseCase(postId)){
                is Resource.Error -> {

                    _state.value = _state.value.copy(
                        isPostLoading = false
                    )

                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = postResult.uiText ?: UiText.unknownError()
                        )
                    )
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isPostLoading = false,
                        post = postResult.data?.post,
                        requesterId = postResult.data?.requesterId
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

    private fun createComment(postId: String){
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true
            )

            when(val createCommentResult = createCommentUseCase(comment = _commentState.value.text, postId)){
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )

                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = createCommentResult.uiText ?: UiText.unknownError()
                        )
                    )
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                    )
                    getCommentsForPost(postId)
                }
            }
        }
    }

    private fun addPostMember(postId: String){
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true
            )

            when(val addPostMemberResult = addPostMemberUseCase(postId)){
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )

                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = addPostMemberResult.uiText ?: UiText.unknownError()
                        )
                    )
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    getPostById(postId)
                    getCommentsForPost(postId)
                }
            }
        }
    }

    private fun deletePost(postId: String){
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true
            )

            when(val deletePostResult = deletePostUseCase(postId)){
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )

                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = deletePostResult.uiText ?: UiText.unknownError()
                        )
                    )
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun deleteComment(commentId: String, postId: String){
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true
            )

            when(val deleteCommentResult = deleteCommentUseCase(commentId)){
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )

                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = deleteCommentResult.uiText ?: UiText.unknownError()
                        )
                    )
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    getCommentsForPost(postId)
                }
            }
        }
    }

    fun refreshScreen(postId: String){
        viewModelScope.launch{
            _isRefreshing.emit(true)
            getPostById(postId)
            getCommentsForPost(postId)
            _isRefreshing.emit(false)
        }
    }



}