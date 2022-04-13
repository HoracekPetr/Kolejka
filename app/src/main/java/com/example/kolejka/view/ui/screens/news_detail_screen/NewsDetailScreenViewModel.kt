package com.example.kolejka.view.ui.screens.news_detail_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kolejka.data.util.Resource
import com.example.kolejka.models.News
import com.example.kolejka.use_cases.news.GetNewsByIdUseCase
import com.example.kolejka.view.util.UiEvent
import com.example.kolejka.view.util.uitext.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailScreenViewModel @Inject constructor(
    private val getNewsByIdUseCase: GetNewsByIdUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _newsDetail = mutableStateOf<News?>(null)
    val newsDetail: State<News?> = _newsDetail

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>("newsId")?.let { id ->
            getNewsById(id)
        }
    }

    private fun getNewsById(newsId: String){
        viewModelScope.launch {

            _isLoading.value = true

            when(val response = getNewsByIdUseCase(newsId)){
                is Resource.Error -> {

                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = response.uiText ?: UiText.unknownError()
                        )
                    )

                    _isLoading.value = false

                }
                is Resource.Success -> {
                    _newsDetail.value = response.data?.toNews()
                    _isLoading.value = false
                }
            }
        }
    }

}