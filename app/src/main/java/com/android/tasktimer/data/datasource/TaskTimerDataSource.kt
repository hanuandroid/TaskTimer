package com.app.pryme.networklibrary.datasource

import com.android.tasktimer.database.entity.TaskTimer
import kotlinx.coroutines.flow.Flow

interface TaskTimerDataSource {

    fun getAllTasks(): Flow<MutableList<TaskTimer>>

    suspend fun saveTask(taskTimer: TaskTimer)

    suspend fun updateTask(taskTimer: TaskTimer)

    suspend fun getTaskInfo(taskId: Int): TaskTimer?
}