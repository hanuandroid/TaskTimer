package com.android.tasktimer.utils

import android.content.Context
import com.android.tasktimer.app.TaskTimerApplication
import com.developer.kalert.KAlertDialog

fun showMessage(context: Context, title: String, message: String){

    KAlertDialog(TaskTimerApplication.applicationContext().applicationContext)
        .setTitleText(title)
        .setContentText(message)
        .show()
}