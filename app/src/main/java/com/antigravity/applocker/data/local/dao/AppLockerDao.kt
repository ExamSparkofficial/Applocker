package com.antigravity.applocker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.antigravity.applocker.data.local.entity.IntruderLogEntity
import com.antigravity.applocker.data.local.entity.LockedAppEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppLockerDao {

    @Query("SELECT * FROM locked_apps")
    fun getAllLockedApps(): Flow<List<LockedAppEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLockedApp(app: LockedAppEntity)

    @Query("DELETE FROM locked_apps WHERE packageName = :packageName")
    suspend fun deleteLockedApp(packageName: String)

    @Insert
    suspend fun insertIntruderLog(log: IntruderLogEntity)

    @Query("SELECT * FROM intruder_logs ORDER BY timestamp DESC")
    fun getIntruderLogs(): Flow<List<IntruderLogEntity>>
}
