package com.kelompok9.elinga


import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var db : FirebaseFirestore
        lateinit var notificationManager: NotificationManager
        lateinit var alarmManager: AlarmManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        db = FirebaseFirestore.getInstance()

        //create notifcation channel
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "Elinga Notification",
                "Elinga Notification",
                NotificationManager.IMPORTANCE_HIGH,
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(channel)
        }

        //Notification stuff here
        val broadcastReceiver = Broadcast()
        val filter = IntentFilter()
        filter.addAction("get db items")
        filter.addAction("schedule notifications")
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter)

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val calendar  = Calendar.getInstance()
        val intent = Intent(this, Broadcast::class.java)
        intent.action = "get db items"
        val pendingIntent =  PendingIntent.getBroadcast(this, 0, intent, 0)
        calendar.set(Calendar.HOUR_OF_DAY, 1)
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }

}
