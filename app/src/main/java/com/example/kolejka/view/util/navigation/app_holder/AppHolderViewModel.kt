package com.example.kolejka.view.util.navigation.app_holder

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kolejka.use_cases.notifications.GetNotificationsCountUseCase
import com.example.kolejka.use_cases.notifications.SetNotificationsToZeroUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppHolderViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsCountUseCase,
    private val setNotificationsToZeroUseCase: SetNotificationsToZeroUseCase
): ViewModel() {
    private val _notificationsCount = mutableStateOf(0)
    val notificationsCount: State<Int> = _notificationsCount

    private fun setNotificationsCount(count: Int){
        _notificationsCount.value = count
    }

    init {
        viewModelScope.launch {
            //setNotificationsCount(getNotificationsUseCase())
            getNotificationsUseCase().collectLatest { notCount ->
                setNotificationsCount(notCount)
            }
        }
    }

    fun setNotificationsToZero(){
        viewModelScope.launch {
            setNotificationsToZeroUseCase()
            setNotificationsCount(0)
        }
    }
}