package com.antigravity.applocker.presentation.hidden

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antigravity.applocker.data.local.dao.HiddenAppDao
import com.antigravity.applocker.data.local.entity.HiddenAppEntity
import com.antigravity.applocker.lockengine.AppLockerDeviceAdminReceiver
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class HiddenAppItem(
    val appName: String,
    val packageName: String,
    val icon: android.graphics.drawable.Drawable,
    val isHidden: Boolean
)

@HiltViewModel
class HiddenAppsViewModel @Inject constructor(
    private val hiddenAppDao: HiddenAppDao,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val packageManager = context.packageManager
    private val devicePolicyManager = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
    private val adminComponent = ComponentName(context, AppLockerDeviceAdminReceiver::class.java)

    private val _installedApps = MutableStateFlow<List<ApplicationInfo>>(emptyList())
    
    val isDeviceOwner = MutableStateFlow(devicePolicyManager.isDeviceOwnerApp(context.packageName))

    val hiddenAppsState: StateFlow<List<HiddenAppItem>> = combine(
        _installedApps,
        hiddenAppDao.getAllHiddenApps()
    ) { installed, hiddenEntities ->
        val hiddenPackages = hiddenEntities.map { it.packageName }.toSet()
        installed.map { info ->
            HiddenAppItem(
                appName = packageManager.getApplicationLabel(info).toString(),
                packageName = info.packageName,
                icon = packageManager.getApplicationIcon(info),
                isHidden = hiddenPackages.contains(info.packageName)
            )
        }.sortedBy { it.appName.lowercase() }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        loadInstalledApps()
    }

    private fun loadInstalledApps() {
        viewModelScope.launch {
            val intent = android.content.Intent(android.content.Intent.ACTION_MAIN, null).apply {
                addCategory(android.content.Intent.CATEGORY_LAUNCHER)
            }
            // Use MATCH_UNINSTALLED_PACKAGES so we can still see hidden apps in the query!
            val resolveInfos = packageManager.queryIntentActivities(intent, PackageManager.MATCH_UNINSTALLED_PACKAGES)
            val apps = resolveInfos.map { it.activityInfo.applicationInfo }
                .distinctBy { it.packageName }
                .filter { it.packageName != context.packageName } // don't hide ourselves here
            _installedApps.value = apps
        }
    }

    fun toggleAppHidden(packageName: String, hide: Boolean) {
        viewModelScope.launch {
            try {
                if (devicePolicyManager.isDeviceOwnerApp(context.packageName)) {
                    devicePolicyManager.setApplicationHidden(adminComponent, packageName, hide)
                }
                
                if (hide) {
                    hiddenAppDao.insertHiddenApp(HiddenAppEntity(packageName))
                } else {
                    hiddenAppDao.deleteHiddenApp(packageName)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
