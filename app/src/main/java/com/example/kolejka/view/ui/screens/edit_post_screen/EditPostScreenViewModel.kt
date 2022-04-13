package com.example.kolejka.view.ui.screens.edit_post_screen

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.cloudinary.android.preprocess.BitmapEncoder
import com.cloudinary.android.preprocess.ImagePreprocessChain
import com.example.kolejka.data.util.Resource
import com.example.kolejka.models.Post
import com.example.kolejka.use_cases.post.EditPostUseCase
import com.example.kolejka.use_cases.post.GetPostByIdUseCase
import com.example.kolejka.view.util.Constants
import com.example.kolejka.view.util.Screen
import com.example.kolejka.view.util.UiEvent
import com.example.kolejka.view.util.states.NewPostRadioState
import com.example.kolejka.view.util.states.StandardTextfieldState
import com.example.kolejka.view.util.uitext.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPostScreenViewModel @Inject constructor(
    private val getPostByIdUseCase: GetPostByIdUseCase,
    private val editPostUseCase: EditPostUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _pickedImageUri = mutableStateOf<Uri?>(null)
    val pickedImageUri = _pickedImageUri

    private val _imageUrl = mutableStateOf<String?>(null)
    val imageUrl = _imageUrl

    private val _newImageUrl = mutableStateOf<String?>(null)
    val newImageUrl: State<String?> = _newImageUrl

    private val _post = mutableStateOf<Post?>(null)
    val post = _post

    private var _titleState = mutableStateOf(StandardTextfieldState())
    var titleState: State<StandardTextfieldState> = _titleState

    private var _descriptionState = mutableStateOf(StandardTextfieldState())
    var descriptionState: State<StandardTextfieldState> = _descriptionState

    private var _locationState = mutableStateOf(StandardTextfieldState())
    var locationState: State<StandardTextfieldState> = _locationState

    private var _limitState = mutableStateOf(StandardTextfieldState())
    var limitState: State<StandardTextfieldState> = _limitState

    private val _priceState = mutableStateOf(StandardTextfieldState())
    val priceState: State<StandardTextfieldState> = _priceState

    private val _priceVisibility = mutableStateOf(false)
    val priceVisibility: State<Boolean> = _priceVisibility

    private var _showCalendarView = mutableStateOf(false)
    var showCalendarView: State<Boolean> = _showCalendarView

    private var _selectedDate = mutableStateOf(Constants.NO_DATE_SELECTED)
    var selectedDate: State<String> = _selectedDate

    private val _postType = mutableStateOf<Int?>(null)
    val postType: State<Int?> = _postType

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _imageUploading = mutableStateOf(false)
    val imageUploading: State<Boolean> = _imageUploading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _postId = mutableStateOf<String?>(null)
    val postId: State<String?> = _postId

    init {
        println("INIT EDIT POST VIEW MODEL")
        savedStateHandle.get<String>("postId")?.let{ postId ->
            println("POST ID: $postId")
            getPostById(postId)

            _postId.value = postId
        }
    }

    fun onEvent(event: EditPostEvent){
        when(event){
            is EditPostEvent.CalendarEnabled -> {
                _showCalendarView.value = !event.enabled
            }
            is EditPostEvent.CropImage -> {
                _pickedImageUri.value = event.uri
            }
            is EditPostEvent.EnteredDescription -> {
                _descriptionState.value = _descriptionState.value.copy(
                    text = event.description
                )
            }
            is EditPostEvent.EnteredLimit -> {
                _limitState.value = _limitState.value.copy(
                    text = event.limit
                )
            }
            is EditPostEvent.EnteredLocation -> {
                _locationState.value = _locationState.value.copy(
                    text = event.location
                )
            }
            is EditPostEvent.EnteredTitle -> {
                _titleState.value = _titleState.value.copy(
                    text = event.title
                )
            }
            is EditPostEvent.PickedImage -> {
                _pickedImageUri.value = event.uri
            }
            is EditPostEvent.SelectDate -> {
                _selectedDate.value = event.date
            }
            EditPostEvent.UpdatePost -> {
                TODO()
            }
            is EditPostEvent.EnteredPrice -> {
                _priceState.value = _priceState.value.copy(
                    text = event.price
                )
            }
            EditPostEvent.SetPriceVisibility -> {
                _priceVisibility.value = !_priceVisibility.value
            }
        }
    }

    private fun getPostById(postId: String){
        viewModelScope.launch {

            _isLoading.value = true

            println("POST ID: $postId")

            when(val postResult = getPostByIdUseCase(postId)){
                is Resource.Error -> {

                   _isLoading.value = false

                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = postResult.uiText ?: UiText.unknownError()
                        )
                    )
                }
                is Resource.Success -> {
                    _isLoading.value = false
                    //_post.value = postResult.data?.post
                    _titleState.value = _titleState.value.copy(
                        text = postResult.data?.post?.title ?: ""
                    )

                    _descriptionState.value = _descriptionState.value.copy(
                        text = postResult.data?.post?.description ?: ""
                    )

                    _locationState.value = _locationState.value.copy(
                        text = postResult.data?.post?.location ?: ""
                    )

                    _selectedDate.value = postResult.data?.post?.date ?: ""

                    _limitState.value = _limitState.value.copy(
                        text = postResult.data?.post?.limit?.toString() ?: ""
                    )

                    _imageUrl.value = postResult.data?.post?.postPictureUrl

                    _postType.value = postResult.data?.post?.type

                    _priceState.value = _priceState.value.copy(
                        text = postResult.data?.post?.price?.toString() ?: ""
                    )

                    if(postResult.data?.post?.price != null){
                        _priceVisibility.value = !_priceVisibility.value
                    }

                }
            }
        }
    }

    private fun updatePost(postId: String?){
        _isLoading.value = true

        if(postId != null){
            viewModelScope.launch {
                val editPostRequest = editPostUseCase(
                    postId = postId,
                    description = _descriptionState.value.text,
                    title = _titleState.value.text,
                    limit = _limitState.value.text.toIntOrNull(),
                    date = _selectedDate.value,
                    location = _locationState.value.text,
                    postImageUrl = _newImageUrl.value,
                    price = if (_priceState.value.text.isBlank()) null else _priceState.value.text.toIntOrNull()
                )

                when(editPostRequest){
                    is Resource.Error ->  {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                uiText = editPostRequest.uiText ?: UiText.unknownError()
                            )
                        )

                        _isLoading.value = false
                    }
                    is Resource.Success -> {
                        _eventFlow.emit(
                            UiEvent.Navigate(
                                route = Screen.PostDetailScreen.route + "?postId=${_postId.value}"
                            )
                        )

                        _isLoading.value = false
                    }
                }
            }
        }
    }

    fun uploadToCloudinary(context: Context){

        _isLoading.value = true

        if(_pickedImageUri.value == null){
            updatePost(_postId.value)
            return
        }

        MediaManager
            .get()
            .upload(_pickedImageUri.value)
            .preprocess(ImagePreprocessChain().saveWith(BitmapEncoder(BitmapEncoder.Format.JPEG, 85)))
            .callback(object : UploadCallback {
                override fun onStart(requestId: String?) {
                    _isLoading.value = true
                }

                override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                }

                override fun onSuccess(
                    requestId: String?,
                    resultData: MutableMap<Any?, Any?>?
                ) {
                    println("CLOUDINARY SUCCESS")
                    _newImageUrl.value = resultData?.getValue("secure_url").toString()
                    updatePost(_postId.value)
                }

                override fun onError(requestId: String?, error: ErrorInfo?) {
                    println("CLOUDINARY FAIL")
                }

                override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                }
            }).dispatch(context)

        _isLoading.value = false
    }
}