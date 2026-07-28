package com.antigravity.applocker

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppLockerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
