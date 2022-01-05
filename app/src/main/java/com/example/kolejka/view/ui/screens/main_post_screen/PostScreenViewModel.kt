package com.example.kolejka.view.ui.screens.main_post_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kolejka.use_cases.auth.AuthenticateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostScreenViewModel @Inject constructor(
    private val authenticateUseCase: AuthenticateUseCase
): ViewModel() {
}