package com.todo.notes.utils

import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.todo.notes.R
import com.todo.notes.broadcast.ReminderReceiver
import com.todo.notes.data.model.NoteDM
import com.todo.notes.ui.home.details.DetailsViewModel
import dagger.android.support.DaggerAppCompatActivity

object AlarmManagerUtil {
    fun scheduleEvent(context: Context, noteDM: NoteDM, notification: Notification) {
        val notificationIntent = Intent(context, ReminderReceiver::class.java)
        val bundle = Bundle()
        bundle.putParcelable(DetailsViewModel.DATA_MODEL, noteDM)
        bundle.putParcelable(ReminderReceiver.NOTIFCATION, notification)
        notificationIntent.putExtras(bundle)
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
                System.currentTimeMillis() + 10 * 1_000L,
                pendingIntent
            )
            log(context, noteDM)
        }
    }

    private fun log(context: Context, noteDM: NoteDM) {
        Log.e(
            "AlarmManagerUtil", context.getString(R.string.alarm_set)
                .replace("*", DateFormatUtil.formatDate(null, noteDM.date).toString())
        )
    }

    fun getNotification(context: Context, content: String?): Notification {
        val builder: Notification.Builder = Notification.Builder(context)
        builder.setContentTitle("Scheduled Notification")
        builder.setContentText(content)
        builder.setSmallIcon(R.mipmap.ic_launcher)
        return builder.build()
    }

}