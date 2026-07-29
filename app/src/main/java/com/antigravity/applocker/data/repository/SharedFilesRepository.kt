package com.antigravity.applocker.data.repository

import android.net.Uri
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedFilesRepository @Inject constructor() {
    private val _sharedUris = MutableStateFlow<List<Uri>>(emptyList())
    val sharedUris: StateFlow<List<Uri>> = _sharedUris
    
    fun setSharedUris(uris: List<Uri>) {
        _sharedUris.value = uris
    }
    
    fun clearSharedUris() {
        _sharedUris.value = emptyList()
    }
}
