package com.example.kolejka.view.util

import com.example.kolejka.view.util.uitext.UiText

sealed class UiEvent {
    data class ShowSnackbar(val uiText: UiText) : UiEvent()
    data class Navigate(val route: String): UiEvent()
}