package com.example.kolejka.view.ui.screens.main_post_screen.sub_post_screens.offer_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.example.kolejka.models.Post
import com.example.kolejka.use_cases.post.GetAllPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfferScreenViewModel @Inject constructor(
    private val getAllPostsUseCase: GetAllPostsUseCase
): ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    var posts = getAllPostsUseCase().cachedIn(viewModelScope)

    fun refreshScreen(posts: LazyPagingItems<Post>){
        viewModelScope.launch{
            _isRefreshing.emit(true)
            posts.refresh()
            _isRefreshing.emit(false)
        }
    }
}