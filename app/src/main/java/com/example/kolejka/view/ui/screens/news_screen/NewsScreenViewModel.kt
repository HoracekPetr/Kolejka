package com.example.kolejka.view.ui.screens.news_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.example.kolejka.data.features.news.dto.NewsDto
import com.example.kolejka.use_cases.news.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsScreenViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
): ViewModel() {

    var news = getNewsUseCase().cachedIn(viewModelScope)

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    fun refreshScreen(news: LazyPagingItems<NewsDto>){
        viewModelScope.launch{
            _isRefreshing.emit(true)
            news.refresh()
            _isRefreshing.emit(false)
        }
    }
}