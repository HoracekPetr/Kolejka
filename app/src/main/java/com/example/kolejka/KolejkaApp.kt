package com.example.kolejka

import android.app.Application
import com.cloudinary.android.MediaManager
import com.example.kolejka.view.util.CloudinaryConsts
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KolejkaApp:Application() {
    companion object {
        var notificationsCount = 0
        private const val ONESIGNAL_APP_ID = "ONESIGNAL"
    }

    override fun onCreate() {
        super.onCreate()

        val config: HashMap<String, String> = HashMap()

        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)

        config["cloud_name"] = CloudinaryConsts.cloud
        config["api_key"] = CloudinaryConsts.api_key
        config["api_secret"] = CloudinaryConsts.secret
        MediaManager.init(applicationContext, config)
    }
}