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

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())
        appLockerEngine.start()
        
        // Start GestureOverlayService
        try {
            val gestureIntent = Intent(this, com.antigravity.applocker.triggers.GestureOverlayService::class.java)
            startService(gestureIntent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
