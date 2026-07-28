package com.antigravity.applocker.lockengine

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.util.Log
import com.antigravity.applocker.domain.repository.AppLockerRepository
import com.antigravity.applocker.presentation.lock.LockActivity
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import com.antigravity.applocker.lockengine.AppLockerDeviceAdminReceiver
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
    private val devicePolicyManager = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
    private val adminComponent = ComponentName(context, AppLockerDeviceAdminReceiver::class.java)
    
    private var pollingJob: Job? = null
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    // Keeps track of the last unlocked app so we don't lock it again immediately
    private var lastUnlockedApp: String? = null
    private var lastUnlockedTime: Long = 0
    
    // Keeps track of temporarily unhidden app
    private var tempUnhiddenApp: String? = null

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

    fun setTemporarilyUnhiddenApp(packageName: String) {
        tempUnhiddenApp = packageName
    }

    private suspend fun checkTopActivity() {
        val topInfo = getTopActivityInfo() ?: return
        val topPackage = topInfo.first
        val topClass = topInfo.second
        
        // Handle temporarily unhidden app
        val temp = tempUnhiddenApp
        if (temp != null && topPackage != temp && topPackage != context.packageName) {
            // User left the temporarily unhidden app and is not in our vault
            try {
                if (devicePolicyManager.isDeviceOwnerApp(context.packageName)) {
                    devicePolicyManager.setApplicationHidden(adminComponent, temp, true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            tempUnhiddenApp = null
        }

        // Never lock our own LockActivity to prevent infinite loops
        if (topPackage == context.packageName && topClass.contains("LockActivity")) return

        val lockedApps = repository.getAllLockedApps().firstOrNull() ?: emptyList()
        val isLocked = lockedApps.any { it.packageName == topPackage }

        if (topPackage != context.packageName && topPackage != lastUnlockedApp) {
            // User left the unlocked app, clear the grace state so it locks instantly next time
            lastUnlockedApp = null
        }

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

    private fun getTopActivityInfo(): Pair<String, String>? {
        val endTime = System.currentTimeMillis()
        val beginTime = endTime - 10000
        
        var topPackageName: String? = null
        var topClassName: String? = null
        val usageEvents = usageStatsManager.queryEvents(beginTime, endTime)
        val event = UsageEvents.Event()
        
        while (usageEvents.hasNextEvent()) {
            usageEvents.getNextEvent(event)
            if (event.eventType == UsageEvents.Event.ACTIVITY_RESUMED) {
                topPackageName = event.packageName
                topClassName = event.className
            }
        }
        return if (topPackageName != null && topClassName != null) Pair(topPackageName, topClassName) else null
    }
}
