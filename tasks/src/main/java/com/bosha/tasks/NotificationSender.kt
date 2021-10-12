package com.bosha.tasks

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.toBitmap
import com.bosha.feature_schedule.R
import java.util.*

class NotificationSender(private val appContext: Context) {

    private var notificationManager: NotificationManagerCompat? = null

    init {
        require(appContext is Application)
        notificationManager = NotificationManagerCompat.from(appContext)
        createChannel()
    }

    private val builder by lazy {
        NotificationCompat.Builder(appContext, CHANNEL_ID)
//            .setGroup("Timer group")
//            .setGroupSummary(false)
            .setWhen(Date().time)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_MAX)
//            .setContentIntent(getStartActivityPendingIntent())
//            .setSilent(true)
//            .setLargeIcon(
//                BitmapFactory.decodeResource(application.resources,
//                    R.mipmap.ic_launcher_round))
            .setSmallIcon(R.drawable.ic_baseline_schedule_24)
//            .addAction(NotificationCompat.Action(null, "Close", getStopPendingIntent()))
    }

    private fun getNotification(title: String, text: String, image: Drawable) =
        builder
            .setContentText(text)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(image.toBitmap())
                    .setBigContentTitle(title)
            )
            .build()


    private fun createChannel() {
        if (notificationManager?.getNotificationChannelCompat(CHANNEL_ID) == null) {
            NotificationChannelCompat.Builder(
                CHANNEL_ID,
                NotificationManagerCompat.IMPORTANCE_HIGH
            )
                .setName("Scheduler")
                .setDescription("Scheduler can plan your movie on he future")
                .build()
                .also {
                    notificationManager?.createNotificationChannel(it)
                }
        }
    }


    fun show(title: String, text: String, image: Drawable) {
        notificationManager?.notify(
            NOTIFICATION_ID,
            getNotification(title, text, image)
        )
    }

    private companion object {
        private const val CHANNEL_ID = "Channel_ID"
        private const val NOTIFICATION_ID = 111 + 222 + 333
    }
}