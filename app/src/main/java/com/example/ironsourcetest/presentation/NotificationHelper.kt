package com.example.ironsourcetest.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.ironsourcetest.R
import com.example.ironsourcetest.Utils

object NotificationHelper {

    fun showNotification(context: Context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(NotificationManager::class.java)

            val channel = NotificationChannel(
                "ActionNotificationChannel",
                "Service",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Game Notification Channel"
            }

            notificationManager.createNotificationChannel(channel)

            notification(notificationManager, context)
        } else {
            notification(null, context)
        }
    }

    private fun notification(notificationManager: NotificationManager?, context: Context) {
        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(Utils.getContactIntent())
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notificationBuilder = NotificationCompat.Builder(
            context,
            "ActionNotificationChannel"
        ).also { builder ->
            builder.setSmallIcon(R.drawable.ic_launcher_background)
            builder.setContentTitle("Notification")
            builder.setContentIntent(resultPendingIntent)
            builder.setContentText("Action is Notification!")
            builder.setDefaults(NotificationCompat.DEFAULT_ALL)
            builder.priority = NotificationCompat.PRIORITY_MAX
            builder.setAutoCancel(true)
        }

        notificationManager?.notify(1, notificationBuilder.build())
    }
}