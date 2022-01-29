package com.bosha.tasks

import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
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
                // Закоммитил параметры котрые следует уточнить
//            .setGroup("Timer group")
//            .setGroupSummary(false)
            .setWhen(Date().time)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_MAX)
//            .setSilent(true)
//            .setLargeIcon(
//                BitmapFactory.decodeResource(application.resources,
//                    R.mipmap.ic_launcher_round))
            .setSmallIcon(R.drawable.ic_baseline_schedule_24)
//            .addAction(NotificationCompat.Action(null, "Close", getStopPendingIntent()))
    }

    private fun getNotification(title: String, text: String, image: Drawable, id: String) =
        builder
            .setContentText(text)
            .setContentIntent(newActivityPendingIntent(id))
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

    private fun newActivityPendingIntent(id: String) =
        Intent(null,"moviesdemo://detail/${id}".toUri())
            .let {
                PendingIntent.getActivity(
                    appContext,
                    REQUEST_CODE_PENDING_INTENT,
                    it,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            }


    fun show(title: String, text: String, image: Drawable, id: String) {
        notificationManager?.notify(
            NOTIFICATION_ID,
            getNotification(title, text, image, id)
        )
    }

    private companion object {
        private const val CHANNEL_ID = "Channel_ID"
        private const val NOTIFICATION_ID = 111 + 222 + 333
        private const val REQUEST_CODE_PENDING_INTENT = 777
    }
}