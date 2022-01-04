package com.example.kolejka.view.ui.screens.main_post_screen.sub_post_screens.offer_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.kolejka.use_cases.post.GetAllPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OfferScreenViewModel @Inject constructor(
    private val getAllPostsUseCase: GetAllPostsUseCase
): ViewModel() {

    val posts = getAllPostsUseCase().cachedIn(viewModelScope)

}