package com.android.tasktimer.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.tasktimer.database.entity.TaskTimer
import com.android.tasktimer.databinding.ViewTaskItemBinding
import com.android.tasktimer.listeners.TaskListListener
import com.android.tasktimer.utils.TimerUtil
import java.text.SimpleDateFormat
import java.util.*

class TaskListAdapter : ListAdapter<TaskTimer, TaskListAdapter.TaskViewHolder>(TaskComparator()) {

    private var listener: TaskListListener? = null
    private var dateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault())

    fun setListener(listener: TaskListListener?) {
        this.listener = listener
    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {

        val itemBinding =
            ViewTaskItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return TaskViewHolder(itemBinding, parent.context)
    }

    inner class TaskViewHolder(
        val itemBinding: ViewTaskItemBinding,
        val context: Context
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun setData(taskTimer: TaskTimer) {

            itemBinding.txtTaskTitle.text = taskTimer.title

            itemBinding.txtTaskDesc.text = taskTimer.desc

            itemBinding.viewRow.setOnClickListener{
                listener?.onTaskTimerItemClick(taskTimer)
            }

            try {
                if (taskTimer.startTime != null && taskTimer.stopTime != null) {
                    val startTime = dateFormat.parse(taskTimer.startTime)
                    val stopTime = dateFormat.parse(taskTimer.stopTime)
                    val diff = startTime.time - stopTime.time
                    val rdate = Date(System.currentTimeMillis() + diff)
                    val time = Date().time - rdate.time
                    itemBinding.txtTaskTotalTime.text = TimerUtil.timeStringFromLong(time)
                }
            }catch (ex: Exception){

            }
        }
    }

    // binds the list items to a view
    override fun onBindViewHolder(viewHolder: TaskViewHolder, position: Int) {
        val generalItem = getItem(position)
        viewHolder.setData(generalItem)

    }

    class TaskComparator : DiffUtil.ItemCallback<TaskTimer>() {
        override fun areItemsTheSame(oldItem: TaskTimer, newItem: TaskTimer): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: TaskTimer, newItem: TaskTimer): Boolean {
            return oldItem.updatedDate == newItem.updatedDate
        }
    }
}