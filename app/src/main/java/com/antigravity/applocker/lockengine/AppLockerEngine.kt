package com.antigravity.applocker.lockengine

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.util.Log
import com.antigravity.applocker.domain.repository.AppLockerRepository
import com.antigravity.applocker.presentation.lock.LockActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppLockerEngine @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: AppLockerRepository
) {
    private val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
    private var pollingJob: Job? = null
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    // Keeps track of the last unlocked app so we don't lock it again immediately
    private var lastUnlockedApp: String? = null
    private var lastUnlockedTime: Long = 0

    fun start() {
        if (pollingJob?.isActive == true) return
        
        pollingJob = coroutineScope.launch {
            while (isActive) {
                checkTopActivity()
                delay(200) // Poll every 200ms
            }
        }
    }

    fun stop() {
        pollingJob?.cancel()
        pollingJob = null
    }
    
    fun setLastUnlockedApp(packageName: String) {
        lastUnlockedApp = packageName
        lastUnlockedTime = System.currentTimeMillis()
    }

    private suspend fun checkTopActivity() {
        val topPackage = getTopPackageName() ?: return
        
        // Skip if it's our own app to prevent locking ourselves in a loop, unless explicitly locked
        if (topPackage == context.packageName) return
        
        // Check if app was recently unlocked
        if (topPackage == lastUnlockedApp && (System.currentTimeMillis() - lastUnlockedTime) < 5000) {
            // Give the user 5 seconds of grace period or implement a better state machine later
            return
        }

        val lockedApps = repository.getAllLockedApps().firstOrNull() ?: emptyList()
        val isLocked = lockedApps.any { it.packageName == topPackage }

        if (isLocked && topPackage != lastUnlockedApp) {
            showLockScreen(topPackage)
        }
    }

    private fun showLockScreen(packageName: String) {
        Log.d("AppLockerEngine", "Locking app: $packageName")
        val intent = Intent(context, LockActivity::class.java).apply {
            putExtra(LockActivity.EXTRA_PACKAGE_NAME, packageName)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)
    }

    private fun getTopPackageName(): String? {
        val endTime = System.currentTimeMillis()
        val beginTime = endTime - 10000
        
        var topPackageName: String? = null
        val usageEvents = usageStatsManager.queryEvents(beginTime, endTime)
        val event = UsageEvents.Event()
        
        while (usageEvents.hasNextEvent()) {
            usageEvents.getNextEvent(event)
            if (event.eventType == UsageEvents.Event.ACTIVITY_RESUMED) {
                topPackageName = event.packageName
            }
        }
        return topPackageName
    }
}
