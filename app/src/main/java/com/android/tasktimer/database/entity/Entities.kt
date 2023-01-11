package com.android.tasktimer.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cw_tasktimer")
data class TaskTimer(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var desc: String,
    var status: Int,
    var totalTime: Long,
    var createdDate: Long,
    var updatedDate: Long? = null,
    var tag: String? = "",
    var startTime: String? = null,
    var stopTime: String? = null,
    var timerCounting: Boolean = false
)

enum class TaskStatus(val type: Int) {
    PAUSE(0),
    PLAY(1),
    STOPPED(2)
}