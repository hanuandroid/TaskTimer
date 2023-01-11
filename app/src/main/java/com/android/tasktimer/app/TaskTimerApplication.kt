package com.android.tasktimer.app

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.android.tasktimer.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class TaskTimerApplication : MultiDexApplication(){

    init {
        instance = this
    }

    companion object {

        private var instance: TaskTimerApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}