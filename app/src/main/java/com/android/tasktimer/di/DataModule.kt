package com.android.tasktimer.di


import com.android.tasktimer.database.coroutines.DefaultDispatcherProvider
import com.android.tasktimer.database.coroutines.DispatcherProvider
import com.app.pryme.networklibrary.datasource.TaskTimerDataSource
import com.app.pryme.networklibrary.datasource.TaskTimerDataSourceImpl
import com.app.pryme.networklibrary.repository.TaskTimerRepository
import com.app.pryme.networklibrary.repository.TaskTimerRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {


    @Provides
    @Singleton
    fun provideTaskTimerDataSource(taskTimerDataSourceImpl: TaskTimerDataSourceImpl): TaskTimerDataSource =
        taskTimerDataSourceImpl


    @Provides
    @Singleton
    fun provideTaskTimerRepository(
        dispatcherProvider: DispatcherProvider,
        taskTimerDataSource: TaskTimerDataSource,
    ): TaskTimerRepository {
        return TaskTimerRepositoryImpl(dispatcherProvider, taskTimerDataSource)
    }

    @Provides
    @Singleton
    internal fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()

}