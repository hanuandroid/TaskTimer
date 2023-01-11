package com.android.tasktimer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.android.tasktimer.R
import com.android.tasktimer.databinding.ActivityNewTaskBinding
import com.android.tasktimer.listeners.TaskTimerListener
import com.android.tasktimer.viewmodel.TaskTimerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewTaskActivity : AppCompatActivity(), TaskTimerListener {

    private lateinit var dataBinding: ActivityNewTaskBinding

    val taskTimerViewModel: TaskTimerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_new_task)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_task)
        taskTimerViewModel.listener = this
        dataBinding.viewModel = taskTimerViewModel
    }

    override fun onTaskCreated() {
        finish()
    }
}