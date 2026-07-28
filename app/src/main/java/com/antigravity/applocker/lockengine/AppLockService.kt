package com.antigravity.applocker.lockengine

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AppLockService : Service() {

    @Inject
    lateinit var appLockerEngine: AppLockerEngine

    companion object {
        private const val CHANNEL_ID = "AppLockerServiceChannel"
        private const val NOTIFICATION_ID = 1
    }

    private var windowManager: android.view.WindowManager? = null
    private var overlayView: android.view.View? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())
        appLockerEngine.start()
        
        // Start Gesture Overlay directly inside this Foreground Service
        if (android.provider.Settings.canDrawOverlays(this)) {
            setupGestureOverlay()
        }
    }

    private fun setupGestureOverlay() {
        windowManager = getSystemService(Context.WINDOW_SERVICE) as android.view.WindowManager
        overlayView = android.view.View(this)
        
        overlayView?.setOnTouchListener(object : android.view.View.OnTouchListener {
            var startY = 0f
            override fun onTouch(v: android.view.View, event: android.view.MotionEvent): Boolean {
                if (event.pointerCount == 2) {
                    when (event.actionMasked) {
                        android.view.MotionEvent.ACTION_POINTER_DOWN -> {
                            startY = (event.getY(0) + event.getY(1)) / 2
                        }
                        android.view.MotionEvent.ACTION_POINTER_UP -> {
                            val endY = (event.getY(0) + event.getY(1)) / 2
                            if (endY - startY > 100) { // Swiped down
                                launchVault()
                            }
                        }
                    }
                }
                return true
            }
        })

        val params = android.view.WindowManager.LayoutParams(
            android.view.WindowManager.LayoutParams.MATCH_PARENT,
            100, // Very thin strip at the top
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) 
                android.view.WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY 
            else 
                android.view.WindowManager.LayoutParams.TYPE_PHONE,
            android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    android.view.WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            android.graphics.PixelFormat.TRANSLUCENT
        )
        params.gravity = android.view.Gravity.TOP or android.view.Gravity.CENTER_HORIZONTAL
        
        try {
            windowManager?.addView(overlayView, params)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun launchVault() {
        val launchIntent = Intent(this, com.antigravity.applocker.presentation.lock.LockActivity::class.java).apply {
            putExtra(com.antigravity.applocker.presentation.lock.LockActivity.EXTRA_PACKAGE_NAME, "HIDDEN_VAULT")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(launchIntent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val unlockedPackage = intent?.getStringExtra("UNLOCKED_PACKAGE")
        if (unlockedPackage != null) {
            appLockerEngine.setLastUnlockedApp(unlockedPackage)
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        appLockerEngine.stop()
        if (overlayView != null) {
            try {
                windowManager?.removeView(overlayView)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("AppLocker")
            .setContentText("App protection is active")
            .setSmallIcon(android.R.drawable.ic_secure) // Replace with your app icon later
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "App Locker Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Keeps the app lock engine running in the background"
            }
            
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(serviceChannel)
        }
    }
}
