package com.app.pryme.networklibrary.datasource

import com.android.tasktimer.database.dao.TaskTimerDao
import com.android.tasktimer.database.entity.TaskTimer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class TaskTimerDataSourceImpl @Inject constructor(private val taskTimerDao: TaskTimerDao) : TaskTimerDataSource {
    override fun getAllTasks(): Flow<MutableList<TaskTimer>> {
        return taskTimerDao.getAllTasks()
    }

    override suspend fun saveTask(taskTimer: TaskTimer) {
        taskTimerDao.insert(taskTimer)
    }

    override suspend fun updateTask(taskTimer: TaskTimer) {
        taskTimerDao.update(taskTimer)
    }

    override suspend fun getTaskInfo(taskId: Int): TaskTimer? {
        return taskTimerDao.getTaskInfo(taskId)
    }

}