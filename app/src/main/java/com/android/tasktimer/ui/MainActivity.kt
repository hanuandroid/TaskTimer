package com.android.tasktimer.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.tasktimer.R
import com.android.tasktimer.database.entity.TaskTimer
import com.android.tasktimer.databinding.ActivityMainBinding
import com.android.tasktimer.listeners.TaskListListener
import com.android.tasktimer.ui.adapters.TaskListAdapter
import com.android.tasktimer.viewmodel.TaskTimerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), TaskListListener {

    private lateinit var dataBinding: ActivityMainBinding

    val taskTimerViewModel: TaskTimerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBinding.viewModel = taskTimerViewModel


        val adapter = TaskListAdapter()
        adapter.setListener(this)
        dataBinding.recyclerview.adapter = adapter
        dataBinding.recyclerview.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            taskTimerViewModel.taskList.collect { taskList ->
                // Update the cached copy of the words in the adapter.
                taskList.let { adapter.submitList(it) }
            }
        }
    }

    override fun onTaskTimerItemClick(taskTimer: TaskTimer) {
        val intent = Intent(this, TaskDetailsActivity::class.java)
        intent.putExtra("task_id",taskTimer.id)
        startActivity(intent)
    }
}