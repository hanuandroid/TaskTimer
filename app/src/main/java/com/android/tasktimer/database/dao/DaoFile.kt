package com.android.tasktimer.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.tasktimer.database.entity.TaskTimer
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TaskTimerDao : BaseDao<TaskTimer> {

    /**
     * Get all data from the cw_tasktime
     */
    @Query("SELECT * FROM cw_tasktimer")
    abstract fun getData(): List<TaskTimer>

    @Query("SELECT * FROM cw_tasktimer")
    abstract fun getAllTasks(): Flow<MutableList<TaskTimer>>

    @Query("SELECT * from cw_tasktimer")
    abstract suspend fun getDataList(): List<TaskTimer>

    @Query("SELECT * from cw_tasktimer where id = :taskId")
    abstract suspend fun getTaskInfo(taskId: Int): TaskTimer?
}

