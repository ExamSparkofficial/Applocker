package com.antigravity.applocker.data.repository

import com.antigravity.applocker.data.local.dao.AppLockerDao
import com.antigravity.applocker.data.local.entity.IntruderLogEntity
import com.antigravity.applocker.data.local.entity.LockedAppEntity
import com.antigravity.applocker.domain.repository.AppLockerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppLockerRepositoryImpl @Inject constructor(
    private val dao: AppLockerDao
) : AppLockerRepository {

    override fun getAllLockedApps(): Flow<List<LockedAppEntity>> = dao.getAllLockedApps()

    override suspend fun lockApp(packageName: String) {
        dao.insertLockedApp(LockedAppEntity(packageName = packageName, isLocked = true))
    }

    override suspend fun unlockApp(packageName: String) {
        dao.deleteLockedApp(packageName)
    }

    override suspend fun addIntruderLog(log: IntruderLogEntity) {
        dao.insertIntruderLog(log)
    }

    override fun getIntruderLogs(): Flow<List<IntruderLogEntity>> = dao.getIntruderLogs()
}
