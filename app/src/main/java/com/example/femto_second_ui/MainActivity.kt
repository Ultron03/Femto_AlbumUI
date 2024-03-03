package com.example.femto_second_ui

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.femto_second_ui.notification.Notification_Activity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var tablayout: TabLayout
    private lateinit var btnNotifiy: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        requestStoragePermission(80)
        viewPager = findViewById(R.id.viewPager)
        viewPagerAdapter = ViewPagerAdapter(this)
        tablayout = findViewById(R.id.tabView)
        viewPager.adapter = viewPagerAdapter
        btnNotifiy = findViewById(R.id.btn_notify)



        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
        } else {
            requestStoragePermission(80);
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 80
            )
        } else {
            Toast.makeText(this, "Hellow---", Toast.LENGTH_SHORT).show()
        }
//        }

        DataExtractionCode.collect_all_photos_mediastore_way(this, true)
        TabLayoutMediator(tablayout, viewPager) { tab: TabLayout.Tab, positon: Int ->
            when (positon) {
                0 -> tab.text = "Photo"
                1 -> tab.text = "Albums"
            }
        }.attach()

        btnNotifiy.setOnClickListener {
            makeNotification()
        }
    }

    private fun requestStoragePermission(request: Int) {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {

        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                READ_EXTERNAL_STORAGE
            )
        ) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("This app requires read access")
                .setTitle("Permission Requied")
                .setCancelable(false)
                .setPositiveButton(
                    "ok"
                ) { dialog, which ->
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        arrayOf<String>(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        request
                    )
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
            builder.show()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(READ_EXTERNAL_STORAGE), request)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 80) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private fun makeNotification() {
        val channelId: String = "CHANNEL_ID"
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle("Notification Title")
            .setContentText("Some Text for Notification Here")
            .addAction(R.drawable.ic_notifications, "Mark as Read",markAsReadPendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val intent = Intent(this, Notification_Activity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("data", "Some Data")

        val pendingIntent =
            PendingIntent.getActivity(this, 101, intent, PendingIntent.FLAG_IMMUTABLE)

        builder.setContentIntent(pendingIntent)
        val notificationManager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            var notificationChannel: NotificationChannel? =
                notificationManager.getNotificationChannel(channelId)

            if (notificationChannel == null) {
                notificationChannel =
                    NotificationChannel(channelId, "Some channel Data", importance)
                notificationChannel.lightColor = Color.BLUE
                notificationChannel.enableVibration(true)
                notificationManager.createNotificationChannel(notificationChannel)
            } else {
                // Update the existing notification channel if necessary
            }
        }
        notificationManager.notify(101, builder.build())
    }

    private val markAsReadPendingIntent: PendingIntent
        get() {
            val intent = Intent(this, MainActivity::class.java)
            Toast.makeText(this,"Cancel this",Toast.LENGTH_SHORT).show()
            intent.action = "com.example.femto_second_ui.CANCEL_NOTIFICATION"
            return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        }
}