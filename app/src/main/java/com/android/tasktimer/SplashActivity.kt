package com.android.tasktimer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.android.tasktimer.ui.MainActivity
import com.android.tasktimer.ui.NewTaskActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({

            moveToNextScreen()

        }, 1000)

    }

    private fun moveToNextScreen() {

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}