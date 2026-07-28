package com.antigravity.applocker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.antigravity.applocker.data.local.dao.AppLockerDao
import com.antigravity.applocker.data.local.entity.IntruderLogEntity
import com.antigravity.applocker.data.local.entity.LockedAppEntity

@Database(
    entities = [LockedAppEntity::class, IntruderLogEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppLockerDatabase : RoomDatabase() {
    abstract val dao: AppLockerDao

    companion object {
        const val DATABASE_NAME = "applocker_db"
    }
}
