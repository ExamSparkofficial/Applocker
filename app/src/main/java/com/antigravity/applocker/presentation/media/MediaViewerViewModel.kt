package com.antigravity.applocker.presentation.media

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antigravity.applocker.data.local.VaultMediaEntity
import com.antigravity.applocker.data.repository.MediaVaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MediaViewerViewModel @Inject constructor(
    private val repository: MediaVaultRepository
) : ViewModel() {

    private val _media = MutableStateFlow<VaultMediaEntity?>(null)
    val media = _media.asStateFlow()

    private val _decryptedBitmap = MutableStateFlow<Bitmap?>(null)
    val decryptedBitmap = _decryptedBitmap.asStateFlow()

    fun loadMedia(id: String) {
        viewModelScope.launch {
            val entity = repository.getMediaById(id)
            _media.value = entity
            
            if (entity?.mimeType?.startsWith("image") == true) {
                withContext(Dispatchers.IO) {
                    try {
                        val inputStream = repository.getDecryptedInputStream(entity)
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        inputStream.close()
                        _decryptedBitmap.value = bitmap
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    fun unhideMedia() {
        val currentMedia = _media.value ?: return
        viewModelScope.launch {
            repository.unhideMedia(currentMedia)
        }
    }

    fun getDecryptedInputStream(entity: VaultMediaEntity): java.io.InputStream {
        return repository.getDecryptedInputStream(entity)
    }
}
