package com.example.kolejka

import android.app.Application
import com.cloudinary.android.MediaManager
import com.example.kolejka.view.util.CloudinaryConsts
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KolejkaApp:Application() {
    companion object {
        var notificationsCount = 0
    }

    override fun onCreate() {
        super.onCreate()

        val config: HashMap<String, String> = HashMap()

        config["cloud_name"] = CloudinaryConsts.cloud
        config["api_key"] = CloudinaryConsts.api_key
        config["api_secret"] = CloudinaryConsts.secret
        MediaManager.init(applicationContext, config)
    }
}