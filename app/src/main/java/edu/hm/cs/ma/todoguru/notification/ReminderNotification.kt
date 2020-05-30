package edu.hm.cs.ma.todoguru.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.database.Task
import edu.hm.cs.ma.todoguru.database.TaskDatabase
import java.time.LocalDateTime
import java.util.Timer
import java.util.TimerTask
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReminderNotification : Service() {

    companion object {
        const val channelID = "todo_guru_notification_channel"
        const val channelName = "ToDo Guru notifications"
        const val channelDescription = "reminder as notification"
        const val notificationInterval = 1_000 * 60
    }

    private val handler = Handler()
    private val timer = Timer()

    private lateinit var builder: NotificationCompat.Builder

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        builder = NotificationCompat.Builder(application, channelID)
            .setSmallIcon(R.drawable.ic_notification_important_24px)
            .setContentTitle(application.getString(R.string.notification_content_title))
            .setContentText(application.getString(R.string.notification_content_description))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        timer.scheduleAtFixedRate(object : TimerTask() {
            private val viewModelJob = Job()
            private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

            override fun run() {
                handler.post {
                    uiScope.launch {
                        if (hasNotifications())
                            sendNotification()
                    }
                }
            }

            private suspend fun hasNotifications(): Boolean {
                return getAllTasks()
                    .filter { it.reminder.isAfter(LocalDateTime.now()) }
                    .count() != 0
            }

            private suspend fun getAllTasks(): List<Task> {
                val dataSource = TaskDatabase.getInstance(application).taskDatabaseDao
                return withContext(Dispatchers.IO) {
                    return@withContext dataSource.getAllTasks()
                }
            }
        }, 0, notificationInterval.toLong())
    }

    fun sendNotification() {
        createNotificationChannel()
        with(NotificationManagerCompat.from(application)) {
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
            applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
