package com.antigravity.applocker.presentation.dashboard

import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antigravity.applocker.domain.model.AppInfo
import com.antigravity.applocker.domain.repository.AppLockerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: AppLockerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    fun loadApps(packageManager: PackageManager) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true) }
            
            repository.getAllLockedApps().collectLatest { lockedApps ->
                val lockedPackages = lockedApps.map { it.packageName }.toSet()
                
                val apps = mutableListOf<AppInfo>()
                val installedPackages = packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
                
                for (packInfo in installedPackages) {
                    if (packageManager.getLaunchIntentForPackage(packInfo.packageName) != null) {
                        val appName = packInfo.applicationInfo.loadLabel(packageManager).toString()
                        val icon = packInfo.applicationInfo.loadIcon(packageManager)
                        val isLocked = lockedPackages.contains(packInfo.packageName)
                        apps.add(AppInfo(packInfo.packageName, appName, icon, isLocked))
                    }
                }
                
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        apps = apps.sortedBy { app -> app.appName }
                    )
                }
            }
        }
    }

    fun toggleAppLock(packageName: String, isCurrentlyLocked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isCurrentlyLocked) {
                repository.unlockApp(packageName)
            } else {
                repository.lockApp(packageName)
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }
}

data class DashboardUiState(
    val apps: List<AppInfo> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = ""
) {
    val filteredApps: List<AppInfo>
        get() = if (searchQuery.isBlank()) {
            apps
        } else {
            apps.filter { it.appName.contains(searchQuery, ignoreCase = true) }
        }
}
