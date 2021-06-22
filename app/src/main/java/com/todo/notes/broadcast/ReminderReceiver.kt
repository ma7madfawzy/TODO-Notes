package com.todo.notes.broadcast

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.todo.notes.data.model.NoteDM
import com.todo.notes.ui.home.details.DetailsViewModel


class ReminderReceiver : BroadcastReceiver() {
    companion object {
        const val NOTIFCATION = "notification"
        const val ACTION="ALARM_ACTION"
    }

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification: Notification? = intent.getParcelableExtra(NOTIFCATION)

        val noteDM: NoteDM? = intent.extras?.getParcelable(DetailsViewModel.DATA_MODEL)
        noteDM?.id?.let { notificationManager.notify(it.toInt(), notification) }
    }

}