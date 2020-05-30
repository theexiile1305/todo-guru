package edu.hm.cs.ma.todoguru.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import edu.hm.cs.ma.todoguru.R

class ReminderNotification(
    private val context: Context,
    taskTitle: String
) {

    companion object {
        const val channelID = "todo_guru_notification_channel"
        const val channelName = "ToDo Guru notifications"
        const val channelDescription = "reminder as notification "
    }

    private val builder = NotificationCompat.Builder(context, channelID)
        .setSmallIcon(R.drawable.ic_notification_important_24px)
        .setContentTitle(context.getString(R.string.notification_content_title))
        .setContentText(
            String.format(
                context.getString(R.string.notification_content_description),
                taskTitle
            )
        )
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)

    fun sendNotification() {
        createNotificationChannel()
        with(NotificationManagerCompat.from(context)) {
            val notificationId: Int = (Math.random() * 1_000).toInt()
            notify(notificationId, builder.build())
        }
    }

    private fun createNotificationChannel() {
        val name = channelName
        val descriptionText = channelDescription
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
            .apply { description = descriptionText }
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
