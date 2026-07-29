package com.antigravity.applocker.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antigravity.applocker.data.local.datastore.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    val uiState: StateFlow<SettingsUiState> = combine(
        settingsDataStore.isDarkMode,
        settingsDataStore.isAmoledMode,
        settingsDataStore.autoLockDelay,
        settingsDataStore.intruderSelfieEnabled,
        settingsDataStore.fakeCrashScreenEnabled
    ) { isDark, isAmoled, delay, selfie, fakeCrash ->
        SettingsUiState(
            isDarkMode = isDark,
            isAmoledMode = isAmoled,
            autoLockDelay = delay,
            intruderSelfieEnabled = selfie,
            fakeCrashScreenEnabled = fakeCrash
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SettingsUiState()
    )

    fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch { settingsDataStore.setDarkMode(enabled) }
    }

    fun toggleAmoledMode(enabled: Boolean) {
        viewModelScope.launch { settingsDataStore.setAmoledMode(enabled) }
    }

    fun setAutoLockDelay(delay: Long) {
        viewModelScope.launch { settingsDataStore.setAutoLockDelay(delay) }
    }

    fun toggleIntruderSelfie(enabled: Boolean) {
        viewModelScope.launch { settingsDataStore.setIntruderSelfieEnabled(enabled) }
    }

    fun toggleFakeCrashScreen(enabled: Boolean) {
        viewModelScope.launch { settingsDataStore.setFakeCrashScreenEnabled(enabled) }
    }
}

data class SettingsUiState(
    val isDarkMode: Boolean = true,
    val isAmoledMode: Boolean = false,
    val autoLockDelay: Long = 0L,
    val intruderSelfieEnabled: Boolean = false,
    val fakeCrashScreenEnabled: Boolean = false
)
