package com.antigravity.applocker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locked_apps")
data class LockedAppEntity(
    @PrimaryKey val packageName: String,
    val isLocked: Boolean = true
)
