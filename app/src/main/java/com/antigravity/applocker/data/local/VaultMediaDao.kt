package com.antigravity.applocker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VaultMediaDao {
    @Query("SELECT * FROM vault_media ORDER BY dateAdded DESC")
    fun getAllMedia(): Flow<List<VaultMediaEntity>>

    @Query("SELECT * FROM vault_media WHERE id = :id")
    suspend fun getMediaById(id: String): VaultMediaEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedia(media: VaultMediaEntity)

    @Delete
    suspend fun deleteMedia(media: VaultMediaEntity)
}
