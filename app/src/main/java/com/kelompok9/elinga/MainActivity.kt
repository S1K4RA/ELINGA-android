package com.kelompok9.elinga


import android.annotation.SuppressLint
import android.app.NotificationManager
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
    }

}
