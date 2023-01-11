package com.app.pryme.networklibrary.repository

import com.android.tasktimer.database.entity.TaskTimer
import com.android.tasktimer.database.coroutines.DispatcherProvider
import com.app.pryme.networklibrary.datasource.TaskTimerDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaskTimerRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val taskTimerDataSource: TaskTimerDataSource
) : TaskTimerRepository {

    override fun getAllTasks(): Flow<MutableList<TaskTimer>> {
        return taskTimerDataSource.getAllTasks()
    }

    override suspend fun saveTask(taskTimer: TaskTimer) {
        withContext(dispatcherProvider.io()) { taskTimerDataSource.saveTask(taskTimer) }
    }

    override suspend fun updateTask(taskTimer: TaskTimer) {
        withContext(dispatcherProvider.io()) { taskTimerDataSource.updateTask(taskTimer) }
    }

    override suspend fun getTaskInfo(taskId: Int): TaskTimer? {
       return withContext(dispatcherProvider.io()) { taskTimerDataSource.getTaskInfo(taskId) }
    }

}