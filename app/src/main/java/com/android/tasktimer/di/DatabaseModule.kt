package com.android.tasktimer.di

import android.content.Context
import androidx.room.Room
import com.android.tasktimer.database.TaskTimerDatabase
import com.android.tasktimer.database.dao.TaskTimerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideTaskTimerDao(appDatabase: TaskTimerDatabase): TaskTimerDao {
        return appDatabase.taskTimerDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): TaskTimerDatabase {
        return TaskTimerDatabase(appContext)
    }
}