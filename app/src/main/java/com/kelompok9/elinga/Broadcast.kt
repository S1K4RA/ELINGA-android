package com.kelompok9.elinga

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class Broadcast : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val string : String? = intent?.getStringExtra("Action")

        if (string == "Send notification 1") {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntenttoApp = TaskStackBuilder.create(context).run {
                addNextIntentWithParentStack(intent)
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            }
            val message = "Notification send with alarm manager"
            val builder = NotificationCompat.Builder(context,"Elinga Notification");
            builder.setContentTitle("NotificationTitle")
            builder.setContentText(message)
            builder.setAutoCancel(true)
            builder.setSmallIcon(R.drawable.ic_launcher_background)
            builder.setPriority(NotificationCompat.PRIORITY_HIGH)
            builder.setDefaults(NotificationCompat.DEFAULT_ALL)
            builder.setContentIntent(pendingIntenttoApp)

            val managerCompat = NotificationManagerCompat.from(context)
            managerCompat.notify(20, builder.build())
        }
        else if (string == "Send notification 2") {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntenttoApp = TaskStackBuilder.create(context).run {
                addNextIntentWithParentStack(intent)
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            }
            val message = "Notification send with second alarm manager "
            val builder = NotificationCompat.Builder(context,"Elinga Notification");
            builder.setContentTitle("NotificationTitle number 2")
            builder.setContentText(message)
            builder.setAutoCancel(true)
            builder.setSmallIcon(R.drawable.ic_launcher_background)
            builder.setPriority(NotificationCompat.PRIORITY_HIGH)
            builder.setDefaults(NotificationCompat.DEFAULT_ALL)
            builder.setContentIntent(pendingIntenttoApp)

            val managerCompat = NotificationManagerCompat.from(context)
            managerCompat.notify(22, builder.build())
        }

        Log.d("Alarm", "onReceive: Alarm fired")
    }
}