package com.example.kolejka.view.ui.screens.splash_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kolejka.data.util.Resource
import com.example.kolejka.use_cases.auth.AuthenticateUseCase
import com.example.kolejka.view.util.Screen
import com.example.kolejka.view.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val authenticateUseCase: AuthenticateUseCase
): ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            when(authenticateUseCase()){
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvent.Navigate(Screen.PostScreen.route)
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.Navigate(Screen.LoginScreen.route)
                    )
                }
            }
        }
    }

}