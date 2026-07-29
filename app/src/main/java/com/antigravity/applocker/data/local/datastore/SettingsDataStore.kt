package com.antigravity.applocker.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "applocker_settings")

@Singleton
class SettingsDataStore @Inject constructor(@ApplicationContext private val context: Context) {

    private val dataStore = context.dataStore

    companion object {
        val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
        val IS_AMOLED_MODE = booleanPreferencesKey("is_amoled_mode")
        val AUTO_LOCK_DELAY = longPreferencesKey("auto_lock_delay") // in ms
        val INTRUDER_SELFIE_ENABLED = booleanPreferencesKey("intruder_selfie_enabled")
        val FAKE_CRASH_SCREEN_ENABLED = booleanPreferencesKey("fake_crash_screen_enabled")
    }

    val isDarkMode: Flow<Boolean> = dataStore.data.map { it[IS_DARK_MODE] ?: true }
    val isAmoledMode: Flow<Boolean> = dataStore.data.map { it[IS_AMOLED_MODE] ?: false }
    val autoLockDelay: Flow<Long> = dataStore.data.map { it[AUTO_LOCK_DELAY] ?: 0L }
    val intruderSelfieEnabled: Flow<Boolean> = dataStore.data.map { it[INTRUDER_SELFIE_ENABLED] ?: false }
    val fakeCrashScreenEnabled: Flow<Boolean> = dataStore.data.map { it[FAKE_CRASH_SCREEN_ENABLED] ?: false }

    suspend fun setDarkMode(enabled: Boolean) {
        dataStore.edit { it[IS_DARK_MODE] = enabled }
    }

    suspend fun setAmoledMode(enabled: Boolean) {
        dataStore.edit { it[IS_AMOLED_MODE] = enabled }
    }

    suspend fun setAutoLockDelay(delayMs: Long) {
        dataStore.edit { it[AUTO_LOCK_DELAY] = delayMs }
    }

    suspend fun setIntruderSelfieEnabled(enabled: Boolean) {
        dataStore.edit { it[INTRUDER_SELFIE_ENABLED] = enabled }
    }

    suspend fun setFakeCrashScreenEnabled(enabled: Boolean) {
        dataStore.edit { it[FAKE_CRASH_SCREEN_ENABLED] = enabled }
    }
}
