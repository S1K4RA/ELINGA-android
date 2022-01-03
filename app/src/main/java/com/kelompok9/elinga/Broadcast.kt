package com.kelompok9.elinga

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.startActivity
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.*

class Broadcast : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {

        if (intent?.action == "get db items") {
            //alarm buat tiap hari
            //val message = "Notification send with alarm manager"
            //val builder = NotificationCompat.Builder(context,"Elinga Notification");
            //builder.setContentTitle("Activity Remainder")
            //builder.setContentText(message)
            //builder.setAutoCancel(true)
            //builder.setSmallIcon(R.drawable.ic_launcher_background)
            //builder.setPriority(NotificationCompat.PRIORITY_HIGH)
            //builder.setDefaults(NotificationCompat.DEFAULT_ALL)
            //builder.setContentIntent(pendingIntenttoApp)

            var pickedDate : LocalDate = LocalDate.now()
            var bulan = pickedDate.month.toString()
            var tanggal = pickedDate.dayOfMonth.toString()
            var tahun = pickedDate.year.toString()
            var collection_name = tanggal + " " + bulan + " " + tahun
            Log.d("db items", collection_name)
            MainActivity.db.collection(collection_name).get().addOnSuccessListener {
                Log.d("db items", it.size().toString())
                for (event in it) {
                    var db_time = event.data.get("time") as Map<*, *>
                    var timelocal = LocalTime.of(db_time.get("hour").toString().toInt(), db_time.get("minute").toString().toInt())

                    val data = Event(
                        event.data.get("name").toString(),
                        timelocal,
                        event.data.get("link").toString(),
                        event.data.get("type").toString())
                    println(data)
                    if (data.name != "null" || data.type != "null") {
                        val intent = Intent(context, Broadcast::class.java)

                        //intent.putExtra("message", data.name.toString())
                        var timelocal = Calendar.getInstance()
                        timelocal.set(
                            Calendar.HOUR_OF_DAY,
                            db_time.get("hour").toString().toInt()
                        )
                        timelocal.set(Calendar.MINUTE, db_time.get("minute").toString().toInt())

                        if (data.type == "Sleep") {
                            Log.d("sleep stuff", "onReceive: turn on do not disturb mode ere")
                            intent.action = "do not disturb mode"
                            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
                            MainActivity.alarmManager.set(
                                AlarmManager.RTC_WAKEUP,
                                timelocal.timeInMillis,
                                pendingIntent
                            )
                        } else {
                            intent.action = "schedule notifications"
                            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
                            MainActivity.alarmManager.set(
                                AlarmManager.RTC,
                                timelocal.timeInMillis,
                                pendingIntent
                            )
                        }
                    }
                }
            }
        }
        else if (intent?.action == "schedule notifications") {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntenttoApp = TaskStackBuilder.create(context).run {
                addNextIntentWithParentStack(intent)
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            }
            val message =  "Activity Starting Soon"
            val builder = NotificationCompat.Builder(context,"Elinga Notification");
            builder.setContentTitle("Alarm")
            builder.setContentText(message)
            builder.setAutoCancel(true)
            builder.setSmallIcon(R.drawable.ic_stat_logo_elinga_icon)
            builder.setPriority(NotificationCompat.PRIORITY_HIGH)
            builder.setDefaults(NotificationCompat.DEFAULT_ALL)
            builder.setContentIntent(pendingIntenttoApp)

            val managerCompat = NotificationManagerCompat.from(context)
            managerCompat.notify(22, builder.build())
            Log.d("notif", "onReceive: notification build")
        }

        else if (intent?.action == "do not disturb mode") {
            Log.d("dnd", "onReceive: whuy")
            val mNotificationManager : NotificationManager = MainActivity.notificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (mNotificationManager.isNotificationPolicyAccessGranted) {
                    //turn off dnd
                    mNotificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE)
                } else {
                    //request permission
                    //Toast.makeText(context, "Please give us access", Toast.LENGTH_SHORT).show()
                    //val dndIntent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                    //startActivity(dndIntent)
                }

            } else {
                Toast.makeText(context, "Your device does not support DND", Toast.LENGTH_SHORT).show()
            }
        }

        Log.d("Alarm", "onReceive: Alarm fired")
    }
}