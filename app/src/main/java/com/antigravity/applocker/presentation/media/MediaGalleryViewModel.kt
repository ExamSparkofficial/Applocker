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
                
                if (com.antigravity.applocker.AppLockerApplication.isDecoyMode) {
                    // Ignore hides in decoy mode
                    return@launch
                }
                try {
                    // 1. Hide media in vault (encrypt and save)
                    val entity = repository.hideMedia(uri, mimeType, name)
                    
                    if (entity != null) {
                        // 2. Delete original using DocumentsContract
                        val deleteUri = android.provider.DocumentsContract.buildDocumentUriUsingTree(uri, android.provider.DocumentsContract.getDocumentId(uri))
                        val deleted = android.provider.DocumentsContract.deleteDocument(context.contentResolver, deleteUri)
                        
                        if (deleted) {
                            // Update UI state handled elsewhere
                        } else {
                            // Sometimes DocumentsContract.deleteDocument fails depending on URI type
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
