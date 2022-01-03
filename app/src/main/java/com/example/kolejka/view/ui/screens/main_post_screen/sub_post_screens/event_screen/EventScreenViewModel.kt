package com.example.kolejka.view.ui.screens.main_post_screen.sub_post_screens.event_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.kolejka.use_cases.GetAllPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventScreenViewModel @Inject constructor(
    private val getAllPostsUseCase: GetAllPostsUseCase
): ViewModel() {

    val posts = getAllPostsUseCase().cachedIn(viewModelScope)

}