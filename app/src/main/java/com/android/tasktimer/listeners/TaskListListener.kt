package com.android.tasktimer.listeners

import com.android.tasktimer.database.entity.TaskTimer
import java.util.*

interface TaskListListener {

    fun onTaskTimerItemClick(taskTimer: TaskTimer)
}