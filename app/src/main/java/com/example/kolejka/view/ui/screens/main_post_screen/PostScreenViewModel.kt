package com.example.kolejka.view.ui.screens.main_post_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kolejka.KolejkaApp
import com.example.kolejka.use_cases.auth.AuthenticateUseCase
import com.example.kolejka.use_cases.notifications.GetNotificationsCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostScreenViewModel @Inject constructor(

): ViewModel() {


}