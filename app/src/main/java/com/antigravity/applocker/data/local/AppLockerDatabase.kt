package com.antigravity.applocker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.antigravity.applocker.data.local.dao.AppLockerDao
import com.antigravity.applocker.data.local.dao.HiddenAppDao
import com.antigravity.applocker.data.local.entity.IntruderLogEntity
import com.antigravity.applocker.data.local.entity.LockedAppEntity
import com.antigravity.applocker.data.local.entity.HiddenAppEntity

@Database(
    entities = [LockedAppEntity::class, IntruderLogEntity::class, HiddenAppEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppLockerDatabase : RoomDatabase() {
    abstract val dao: AppLockerDao
    abstract val hiddenAppDao: HiddenAppDao

    companion object {
        const val DATABASE_NAME = "applocker_db"
    }
}
