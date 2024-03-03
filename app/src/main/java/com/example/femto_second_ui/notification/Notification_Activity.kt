package com.example.femto_second_ui.notification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.femto_second_ui.R

class Notification_Activity : AppCompatActivity() {
    private lateinit var notificationHeading:TextView
    private lateinit var notificationDesc:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        init()


    }

    private fun init(){
        notificationDesc = findViewById(R.id.notification_desc)
    }
}