package com.android.tasktimer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.tasktimer.database.dao.TaskTimerDao
import com.android.tasktimer.database.entity.TaskTimer


@Database(
    entities = [TaskTimer::class],
    version = 1,
    exportSchema = true
)
abstract class TaskTimerDatabase : RoomDatabase() {

    abstract fun taskTimerDao(): TaskTimerDao

    companion object {

        @Volatile
        private var instance: TaskTimerDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        /*
            *
            * Here the passphrase is being hardcoded. This should not be done. It is your responsibility to generate
            * random Secret Key as mentioned in the README file.
            *
            * */

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            TaskTimerDatabase::class.java, "task_timer_database.db"
        ).allowMainThreadQueries().build()
    }
}


