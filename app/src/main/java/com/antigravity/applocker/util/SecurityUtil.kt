package com.antigravity.applocker.util

import android.app.Activity
import android.content.Context
import android.provider.Settings
import android.view.WindowManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecurityUtil @Inject constructor() {

    /**
     * Prevents screenshots and screen recording for the given activity.
     */
    fun preventScreenshots(activity: Activity) {
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }

    /**
     * Allows screenshots and screen recording for the given activity.
     */
    fun allowScreenshots(activity: Activity) {
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
    }

    /**
     * Checks if developer options are enabled.
     */
    fun isDeveloperOptionsEnabled(context: Context): Boolean {
        return Settings.Global.getInt(
            context.contentResolver,
            Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0
        ) != 0
    }

    /**
     * Checks if ADB (USB Debugging) is enabled.
     */
    fun isUsbDebuggingEnabled(context: Context): Boolean {
        return Settings.Global.getInt(
            context.contentResolver,
            Settings.Global.ADB_ENABLED, 0
        ) != 0
    }
}
