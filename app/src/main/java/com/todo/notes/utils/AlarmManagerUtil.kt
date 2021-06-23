package com.todo.notes.utils

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.todo.notes.R
import com.todo.notes.broadcast.ReminderReceiver
import com.todo.notes.data.model.NoteDM
import com.todo.notes.ui.home.details.DetailsActivity
import com.todo.notes.ui.home.details.DetailsViewModel
import dagger.android.support.DaggerAppCompatActivity


object AlarmManagerUtil {
    fun scheduleEvent(context: Context, noteDM: NoteDM) {
        val notification: Notification = getNotification(context, noteDM)
        val notificationIntent = getIntent(context, noteDM, notification)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            noteDM.id?.toInt()!!,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val futureInMillis = DateFormatUtil.calendar(noteDM.date)
        futureInMillis?.let {
            val alarmManager =
                context.getSystemService(DaggerAppCompatActivity.ALARM_SERVICE) as AlarmManager
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                futureInMillis,
                pendingIntent
            )
            log(context, noteDM)
        }
    }

    private fun getIntent(context: Context, noteDM: NoteDM, notification: Notification): Intent {
        val notificationIntent = Intent(context, ReminderReceiver::class.java)
        notificationIntent.putExtra(DetailsViewModel.DATA_MODEL, ParcelableUtil.marshall(noteDM))
        notificationIntent.putExtra(ReminderReceiver.NOTIFICATION, notification)
        return notificationIntent
    }

    private fun log(context: Context, noteDM: NoteDM) {
        Log.e(
            "AlarmManagerUtil", context.getString(R.string.alarm_set)
                .replace("*", DateFormatUtil.formatDate(null, noteDM.date).toString())
        )
    }

    private fun getNotification(context: Context, noteDM: NoteDM): Notification {
        handleChannel(context)

        return Notification.Builder(
            context.applicationContext,
            context.getString(R.string.reminder)
        )
            .setContentTitle(context.getString(R.string.scheduled_notification))
            .setContentText(noteDM.title)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(getContentIntent(context, noteDM))
            .setAutoCancel(true)
            .setStyle(Notification.BigTextStyle().bigText(noteDM.description))
            .setChannelId(handleChannel(context))
            .build()
    }

    private fun handleChannel(context: Context): String {
        val mNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = context.getString(R.string.reminders_channel_id)
        val channelName = context.getString(R.string.reminders_channel)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            mNotificationManager.createNotificationChannel(channel)
        }
        return channelId
    }

    private fun getContentIntent(context: Context, noteDM: NoteDM): PendingIntent? {
        val bundle = Bundle()
        bundle.putParcelable(DetailsViewModel.DATA_MODEL, noteDM)
        val i = Intent(context, DetailsActivity::class.java)
        i.putExtras(bundle)
        return PendingIntent.getActivity(context, 0, i, 0)
    }
}