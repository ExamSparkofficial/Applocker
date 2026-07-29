package com.antigravity.applocker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vault_media")
data class VaultMediaEntity(
    @PrimaryKey val id: String, // UUID
    val originalFileName: String,
    val mimeType: String,
    val encryptedFilePath: String,
    val encryptedThumbnailPath: String,
    val dateAdded: Long
)
