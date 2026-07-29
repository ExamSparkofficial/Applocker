package com.antigravity.applocker.data.security

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecurityPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_auth_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    companion object {
        private const val KEY_HASHED_PIN = "key_hashed_pin"
        private const val KEY_DECOY_HASHED_PIN = "key_decoy_hashed_pin"
        private const val KEY_HASHED_PASSWORD = "key_hashed_password"
        private const val KEY_PATTERN_STRING = "key_pattern_string"
        private const val KEY_SALT = "key_salt"
        private const val KEY_WALLPAPER_URI = "key_wallpaper_uri"
    }

    fun saveHashedPin(hash: String) {
        sharedPreferences.edit().putString(KEY_HASHED_PIN, hash).apply()
    }

    fun getHashedPin(): String? = sharedPreferences.getString(KEY_HASHED_PIN, null)

    fun saveDecoyHashedPin(hash: String) {
        sharedPreferences.edit().putString(KEY_DECOY_HASHED_PIN, hash).apply()
    }

    fun getDecoyHashedPin(): String? = sharedPreferences.getString(KEY_DECOY_HASHED_PIN, null)

    fun saveHashedPassword(hash: String) {
        sharedPreferences.edit().putString(KEY_HASHED_PASSWORD, hash).apply()
    }

    fun getHashedPassword(): String? = sharedPreferences.getString(KEY_HASHED_PASSWORD, null)
    
    fun savePattern(pattern: String) {
        sharedPreferences.edit().putString(KEY_PATTERN_STRING, pattern).apply()
    }
    
    fun getPattern(): String? = sharedPreferences.getString(KEY_PATTERN_STRING, null)

    fun saveSalt(salt: String) {
        sharedPreferences.edit().putString(KEY_SALT, salt).apply()
    }

    fun getSalt(): String? = sharedPreferences.getString(KEY_SALT, null)

    fun saveWallpaperUri(uri: String) {
        sharedPreferences.edit().putString(KEY_WALLPAPER_URI, uri).apply()
    }

    fun getWallpaperUri(): String? = sharedPreferences.getString(KEY_WALLPAPER_URI, null)
}
