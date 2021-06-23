package com.todo.notes.broadcast

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.todo.notes.R
import com.todo.notes.data.model.NoteDM
import com.todo.notes.ui.home.details.DetailsViewModel
import com.todo.notes.utils.ParcelableUtil


class ReminderReceiver : BroadcastReceiver() {
    companion object {
        const val NOTIFICATION = "notification"
        const val ACTION="ALARM_ACTION"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification: Notification? = ParcelableUtil
            .unmarshall(intent.getByteArrayExtra(NOTIFICATION)!!,Notification.CREATOR)

        val noteDM: NoteDM? = ParcelableUtil
            .unmarshall(intent.getByteArrayExtra(DetailsViewModel.DATA_MODEL)!!,NoteDM.CREATOR)
        noteDM?.id?.let { notificationManager.notify(it.toInt(), notification) }
    }
}