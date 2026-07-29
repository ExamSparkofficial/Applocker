package com.antigravity.applocker.presentation.media

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antigravity.applocker.data.repository.MediaVaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

import android.app.PendingIntent
import android.content.IntentSender
import android.os.Build
import android.provider.MediaStore
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@HiltViewModel
class MediaGalleryViewModel @Inject constructor(
    private val repository: MediaVaultRepository
) : ViewModel() {

    val mediaList = repository.getAllMedia()
    
    private val _deleteRequestFlow = MutableSharedFlow<IntentSender>()
    val deleteRequestFlow = _deleteRequestFlow.asSharedFlow()

    fun hideSelectedMedia(uris: List<Uri>, context: Context) {
        viewModelScope.launch {
            val urisToDelete = mutableListOf<Uri>()
            
            for (uri in uris) {
                var name = "Unknown"
                val mimeType = context.contentResolver.getType(uri) ?: "*/*"
                
                context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        if (nameIndex != -1) {
                            name = cursor.getString(nameIndex)
                        }
                    }
                }
                
                repository.hideMedia(uri, mimeType, name)
                
                if (uri.scheme == "content" && uri.authority?.contains("media") == true) {
                    urisToDelete.add(uri)
                }
            }
            
            if (urisToDelete.isNotEmpty() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                try {
                    val pendingIntent = MediaStore.createDeleteRequest(context.contentResolver, urisToDelete)
                    _deleteRequestFlow.emit(pendingIntent.intentSender)
                } catch (e: SecurityException) {
                    e.printStackTrace()
                }
            } else if (urisToDelete.isNotEmpty()) {
                // For older Android versions, we can delete directly
                for (uri in urisToDelete) {
                    try {
                        context.contentResolver.delete(uri, null, null)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}
