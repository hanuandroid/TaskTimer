package com.android.tasktimer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.android.tasktimer.R
import com.android.tasktimer.databinding.ActivityTaskDetailsBinding
import com.android.tasktimer.utils.TimerUtil
import com.android.tasktimer.viewmodel.TaskTimerViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class TaskDetailsActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityTaskDetailsBinding
    val taskTimerViewModel: TaskTimerViewModel by viewModels()

    private val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_task_details)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_task_details)
        dataBinding.viewModel = taskTimerViewModel

        dataBinding.startButton.setOnClickListener { startStopAction() }

        intent.getIntExtra("task_id", 0).let {
            taskTimerViewModel.loadTaskTimerDetails(it)
        }
        loadTaskInfo()
    }

    private fun loadTaskInfo(){
        taskTimerViewModel.taskInfo.observe(this, androidx.lifecycle.Observer { taskTimer ->
            taskTimer?.let {
                taskTimerViewModel.taskTimer = it
                taskTimerViewModel.initializeTimerValues()
                initializeTimer()
            }
        })
    }

    private fun initializeTimer() {

        if (taskTimerViewModel.timerCounting()) {
            startTimer()
        } else {
            stopTimer()
            if (taskTimerViewModel.startTime() != null && taskTimerViewModel.stopTime() != null) {
                calcRestartTime()?.let {
                    val time = Date().time - it.time
                    dataBinding.timeTV.text = TimerUtil.timeStringFromLong(time)
                }
            }
        }

        timer.scheduleAtFixedRate(TimeTask(), 0, 500)
    }

    private inner class TimeTask : TimerTask() {
        override fun run() {
            if (taskTimerViewModel.timerCounting()) {
                taskTimerViewModel.startTime()?.let {
                    val time = Date().time - it.time
                    dataBinding.timeTV.text = TimerUtil.timeStringFromLong(time)
                }

            }
        }
    }

    private fun resetAction() {
        taskTimerViewModel.setStopTime(null)
        taskTimerViewModel.setStartTime(null)
        stopTimer()
        dataBinding.timeTV.text = TimerUtil.timeStringFromLong(0)
    }

    private fun stopTimer() {
        taskTimerViewModel.setTimerCounting(false)
        dataBinding.startButton.text = getString(R.string.str_start)
    }

    private fun startTimer() {
        taskTimerViewModel.setTimerCounting(true)
        dataBinding.startButton.text = getString(R.string.str_stop)
    }

    private fun startStopAction() {
        if (taskTimerViewModel.timerCounting()) {
            taskTimerViewModel.setStopTime(Date())
            stopTimer()
        } else {
            if (taskTimerViewModel.stopTime() != null) {
                taskTimerViewModel.setStartTime(calcRestartTime())
                taskTimerViewModel.setStopTime(null)
            } else {
                taskTimerViewModel.setStartTime(Date())
            }
            startTimer()
        }
    }

    private fun calcRestartTime(): Date? {
        taskTimerViewModel.startTime()?.let { startTime ->
            taskTimerViewModel.stopTime()?.let { stopTime ->
                val diff = startTime.time - stopTime.time
                return Date(System.currentTimeMillis() + diff)
            }
        }
        return null
    }

    override fun onStop() {
        if (taskTimerViewModel.timerCounting()) {
            taskTimerViewModel.setStopTime(Date())
            stopTimer()
        }
        super.onStop()
    }
}