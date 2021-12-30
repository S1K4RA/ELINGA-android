package com.kelompok9.elinga


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var db : FirebaseFirestore
        lateinit var notificationManager: NotificationManager
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

        val mFragmentManager = supportFragmentManager
        val mfHome = Home()

        mFragmentManager.findFragmentByTag(Home::class.java.simpleName)
        mFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainerView, mfHome, Home::class.java.simpleName)
            .commit()
    }

}
