package com.example.kolejka

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KolejkaApp:Application() {
    companion object {
        var notificationsCount = 0
    }
}