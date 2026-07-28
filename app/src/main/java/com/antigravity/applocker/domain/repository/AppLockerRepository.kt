package com.antigravity.applocker.domain.repository

import com.antigravity.applocker.data.local.entity.IntruderLogEntity
import com.antigravity.applocker.data.local.entity.LockedAppEntity
import kotlinx.coroutines.flow.Flow

interface AppLockerRepository {
    fun getAllLockedApps(): Flow<List<LockedAppEntity>>
    suspend fun lockApp(packageName: String)
    suspend fun unlockApp(packageName: String)
    
    suspend fun addIntruderLog(log: IntruderLogEntity)
    fun getIntruderLogs(): Flow<List<IntruderLogEntity>>
}
