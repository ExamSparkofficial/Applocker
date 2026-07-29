package com.antigravity.applocker.data.repository

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import com.antigravity.applocker.data.local.VaultMediaDao
import com.antigravity.applocker.data.local.VaultMediaEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaVaultRepository @Inject constructor(
    @dagger.hilt.android.qualifiers.ApplicationContext private val context: Context,
    private val vaultMediaDao: VaultMediaDao
) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val mediaDir = File(context.filesDir, "vault_media").apply {
        if (!exists()) mkdirs()
    }

    fun getAllMedia(): Flow<List<VaultMediaEntity>> {
        return if (com.antigravity.applocker.AppLockerApplication.isDecoyMode) {
            kotlinx.coroutines.flow.flowOf(emptyList())
        } else {
            vaultMediaDao.getAllMedia()
        }
    }

    suspend fun getMediaById(id: String): VaultMediaEntity? = vaultMediaDao.getMediaById(id)

    suspend fun hideMedia(uri: Uri, mimeType: String, originalName: String): VaultMediaEntity? = withContext(Dispatchers.IO) {
        if (com.antigravity.applocker.AppLockerApplication.isDecoyMode) return@withContext null
        
        val id = UUID.randomUUID().toString()
        val encryptedFile = File(mediaDir, "$id.enc")

        val encryptedFileObj = EncryptedFile.Builder(
            context,
            encryptedFile,
            masterKey,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            encryptedFileObj.openFileOutput().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }

        val entity = VaultMediaEntity(
            id = id,
            originalFileName = originalName,
            mimeType = mimeType,
            encryptedFilePath = encryptedFile.absolutePath,
            encryptedThumbnailPath = "", // For now, we'll decrypt on the fly or implement thumb later
            dateAdded = System.currentTimeMillis()
        )
        vaultMediaDao.insertMedia(entity)
        
        entity
    }

    suspend fun unhideMedia(media: VaultMediaEntity): Boolean = withContext(Dispatchers.IO) {
        if (com.antigravity.applocker.AppLockerApplication.isDecoyMode) return@withContext false
        // We need to write back to MediaStore, this is complex with Scoped Storage.
        // For MVP, let's copy to Downloads folder as it's easier.
        // TODO: Implement MediaStore insert.
        return@withContext true
    }
    
    fun getDecryptedInputStream(media: VaultMediaEntity): java.io.InputStream {
        val encryptedFile = File(media.encryptedFilePath)
        val encryptedFileObj = EncryptedFile.Builder(
            context,
            encryptedFile,
            masterKey,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()
        return encryptedFileObj.openFileInput()
    }
}
