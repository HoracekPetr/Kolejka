package com.example.kolejka.view.ui.screens.notification_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.example.kolejka.R
import com.example.kolejka.data.features.notification.dto.NotificationDto
import com.example.kolejka.data.util.Resource
import com.example.kolejka.use_cases.notifications.DeleteNotificationsForUserUseCase
import com.example.kolejka.use_cases.notifications.GetNotificationsUseCase
import com.example.kolejka.view.util.UiEvent
import com.example.kolejka.view.util.uitext.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationScreenViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val deleteNotificationsForUserUseCase: DeleteNotificationsForUserUseCase
) : ViewModel() {

    private val _clickedDeleteNotifications = mutableStateOf(false)
    val clickedDeleteNotifications: State<Boolean> = _clickedDeleteNotifications

    val notifications = getNotificationsUseCase().cachedIn(viewModelScope)

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()


    fun refreshScreen(notifications: LazyPagingItems<NotificationDto>) {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            notifications.refresh()
            _isRefreshing.emit(false)
        }
    }

    fun onEvent(event: NotificationScreenEvent) {
        when (event) {
            is NotificationScreenEvent.ExpandDeleteNotification -> {
                _clickedDeleteNotifications.value = !_clickedDeleteNotifications.value
            }
            is NotificationScreenEvent.DeleteNotification -> {
                viewModelScope.launch {
                    deleteNotificationsForUser(event.notifications)
                }
            }
        }
    }

    private suspend fun deleteNotificationsForUser(notifications: LazyPagingItems<NotificationDto>) {

        _isLoading.value = true

        when (deleteNotificationsForUserUseCase()) {
            is Resource.Error -> {
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(uiText = UiText.StringResource(R.string.an_unknown_error_occured))
                )

                _isLoading.value = false
            }
            is Resource.Success -> {
                notifications.refresh()
                _isLoading.value = false
            }
        }
    }
}