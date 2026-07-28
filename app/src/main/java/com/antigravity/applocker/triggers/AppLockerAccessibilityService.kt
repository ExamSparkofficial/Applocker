package com.antigravity.applocker.triggers

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import com.antigravity.applocker.presentation.lock.LockActivity

class AppLockerAccessibilityService : AccessibilityService() {
    
    private var volumeUpPressCount = 0
    private var lastVolumeUpTime = 0L
    private val TIME_THRESHOLD = 2000L // 2 seconds to press 3 times

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Not used for our purpose
    }

    override fun onInterrupt() {
        // Not used
    }

    override fun onKeyEvent(event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastVolumeUpTime > TIME_THRESHOLD) {
                volumeUpPressCount = 1
            } else {
                volumeUpPressCount++
            }
            lastVolumeUpTime = currentTime

            if (volumeUpPressCount >= 3) {
                volumeUpPressCount = 0
                launchVault()
                return true // Consume the event
            }
        }
        return super.onKeyEvent(event)
    }

    private fun launchVault() {
        val launchIntent = Intent(this, LockActivity::class.java).apply {
            putExtra(LockActivity.EXTRA_PACKAGE_NAME, "HIDDEN_VAULT")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(launchIntent)
    }
}
