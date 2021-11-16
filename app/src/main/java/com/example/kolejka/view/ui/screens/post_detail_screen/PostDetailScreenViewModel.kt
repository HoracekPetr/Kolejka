package com.example.kolejka.view.ui.screens.post_detail_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostDetailScreenViewModel @Inject constructor(
): ViewModel() {

    private val _availability = mutableStateOf(0)
    val availability: State<Int> = _availability

    fun incrementAvailability(){
        _availability.value++
    }

}