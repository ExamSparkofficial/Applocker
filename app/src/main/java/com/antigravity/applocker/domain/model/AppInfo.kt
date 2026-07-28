package com.antigravity.applocker.domain.model

import android.graphics.drawable.Drawable

data class AppInfo(
    val packageName: String,
    val appName: String,
    val icon: Drawable? = null,
    val isLocked: Boolean = false
)
