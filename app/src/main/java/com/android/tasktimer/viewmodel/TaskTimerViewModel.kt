package com.android.tasktimer.viewmodel

import android.content.Intent
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.tasktimer.database.entity.TaskStatus
import com.android.tasktimer.database.entity.TaskTimer
import com.android.tasktimer.listeners.TaskTimerListener
import com.android.tasktimer.ui.NewTaskActivity
import com.android.tasktimer.utils.showMessage
import com.app.pryme.networklibrary.repository.TaskTimerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@HiltViewModel
class TaskTimerViewModel @Inject constructor(
    private val taskTimerRepository: TaskTimerRepository
) : ViewModel() {

    var listener: TaskTimerListener? = null

    var txtTitleView: ObservableField<String> = ObservableField("")
    var txtDescView: ObservableField<String> = ObservableField("")

    private val _taskInfo = MutableLiveData<TaskTimer>()
    val taskInfo: LiveData<TaskTimer>
        get() = _taskInfo

    var taskTimer: TaskTimer? = null

    /*private val _taskList = MutableLiveData<List<TaskTimer>>()
    val taskList: LiveData<List<TaskTimer>>
        get() = _taskList*/

    val taskList: Flow<MutableList<TaskTimer>> = taskTimerRepository.getAllTasks()

    fun onNewTaskClick(view: View) {
        val intent = Intent(view.context, NewTaskActivity::class.java)
        view.context.startActivity(intent)
    }

    fun onTaskSubmit(view: View) {

        val title = txtTitleView.get().toString()
        val desc = txtDescView.get().toString()

        if (title.isEmpty()) {
            showMessage(view.context, "Alert", "Please enter title")
        } else if (desc.isEmpty()) {
            showMessage(view.context, "Alert", "Please enter description")
        } else {
            viewModelScope.launch {
                taskTimerRepository.saveTask(
                    TaskTimer(
                        0,
                        title = title,
                        desc = desc,
                        status = TaskStatus.PAUSE.type,
                        totalTime = 0,
                        createdDate = System.currentTimeMillis(),
                        updatedDate = System.currentTimeMillis()
                    )
                )
                listener?.onTaskCreated()
            }
        }
    }

    private var timerCounting = false
    private var startTime: Date? = null
    private var stopTime: Date? = null

    private var dateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault())

    fun loadTaskTimerDetails(taskId: Int) {

        viewModelScope.launch {
            val task = taskTimerRepository.getTaskInfo(taskId)
            task?.let {
                _taskInfo.postValue(it)
            }
        }

    }

    fun initializeTimerValues() {
        taskTimer?.let {
            timerCounting = it.timerCounting

            it.startTime?.let {
                startTime = dateFormat.parse(it)
            }

            it.stopTime?.let {
                stopTime = dateFormat.parse(it)
            }
        }
    }

    fun startTime(): Date? = startTime

    fun setStartTime(date: Date?) {
        startTime = date
        date?.let {
            val stringDate = dateFormat.format(it)
            //update Db
            taskTimer?.apply {
                startTime = stringDate
            }
            viewModelScope.launch {
                taskTimer?.let { it1 -> taskTimerRepository.updateTask(it1) }
            }
        }
    }

    fun stopTime(): Date? = stopTime

    fun setStopTime(date: Date?) {
        stopTime = date
        date?.let {
            val stringDate = dateFormat.format(date)

            //update Db
            taskTimer?.apply {
                stopTime = stringDate
            }
            viewModelScope.launch {
                taskTimer?.let { it1 -> taskTimerRepository.updateTask(it1) }
            }
        }

    }

    fun timerCounting(): Boolean = timerCounting

    fun setTimerCounting(value: Boolean)
    {
        timerCounting = value

        taskTimer?.apply {
            timerCounting = value
            updatedDate = System.currentTimeMillis()
        }
        viewModelScope.launch {
            taskTimer?.let { it1 -> taskTimerRepository.updateTask(it1) }
        }
    }
}