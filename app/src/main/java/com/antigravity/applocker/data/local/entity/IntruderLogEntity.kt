package com.antigravity.applocker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "intruder_logs")
data class IntruderLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long,
    val packageName: String,
    val photoPath: String?
)
