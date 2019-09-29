package com.example.moviecatalogue.notification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.widget.Toast
import com.example.moviecatalogue.MainApp
import com.example.moviecatalogue.R
import com.example.moviecatalogue.ext.logD
import com.example.moviecatalogue.ext.toast
import com.example.moviecatalogue.model.Movie
import java.text.SimpleDateFormat
import java.util.*

class NotificationReceiver : BroadcastReceiver() {

    companion object {
        const val TYPE_DAILY = "Daily reminder was setup!"
        const val TYPE_RELEASE = "Release reminder was setup!"
        const val EXTRA_MESSAGE = "message"
        const val EXTRA_TYPE = "type"
        const val DAILY_MESSAGE = "Catalogue Movie missing you!"

        const val DATE_FORMAT = "yyyy-MM-dd"
        const val CHANNEL_ID = "Channel_1"
        const val CHANNEL_NAME = "AlarmManager channel"
        const val HOUR_DAILY = 7
        const val HOUR_RELEASE = 8

        // Siapkan 2 id untuk 2 macam alarm, onetime dna repeating
        private val ID_DAILY = 100
        private val ID_RELEASE = 101

        //        val CHANNEL_NAME = "dicoding channel" as CharSequence
        const val RELEASET_TODAY = " has been relese today!"
    }

    private val main = MainApp()

    private var idNotif = 0
    private var maxNotif = 2
    private val stackNotification: MutableList<Movie> = mutableListOf()


    override fun onReceive(context: Context, intent: Intent?) {
        val type = intent?.getStringExtra(EXTRA_TYPE)
        val message = intent?.getStringExtra(EXTRA_MESSAGE)
        val notifId =
            if (type.equals(TYPE_DAILY, ignoreCase = true)) ID_DAILY else ID_RELEASE
        var title = ""

        if (type.equals(TYPE_DAILY, ignoreCase = true)) {
            title = TYPE_DAILY

            showDailyReminder(context, message, notifId)
        } else {
            title = TYPE_RELEASE
            idNotif = 0
            getReleaseToday {
                stackNotification.addAll(it)
                if (stackNotification.isNotEmpty()) {
                    for (notif in stackNotification) {
                        showReleaseReminder(context, notif, notifId)
                        idNotif++
                    }
                }
            }
        }
        showToast(context, title, message)

    }

    private fun showToast(context: Context?, title: String, message: String?) {
        Toast.makeText(context, "$title : $message", Toast.LENGTH_LONG).show()
    }

    private fun showDailyReminder(context: Context, string: String?, notifId: Int) {
        val mNotificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setContentTitle(string)
            .setContentText(DAILY_MESSAGE)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            mBuilder.setChannelId(CHANNEL_ID)
            mNotificationManager.createNotificationChannel(channel)
        }

        val notification = mBuilder.build();

        mNotificationManager.notify(notifId, notification)
    }

    private fun showReleaseReminder(context: Context, movie: Movie, notifId: Int) {
        val mNotificationManager: NotificationManager? =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        val mBuilder: NotificationCompat.Builder
        if (idNotif < maxNotif) {
            mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(movie.title)
                .setContentText(movie.title + RELEASET_TODAY)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setAutoCancel(true)
        } else {
            val inboxStyle = NotificationCompat.InboxStyle()
                .addLine(movie.title + RELEASET_TODAY)
                .setBigContentTitle(movie.title)

            mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(movie.title)
                .setContentText(RELEASET_TODAY)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setGroupSummary(true)
                .setStyle(inboxStyle)
                .setAutoCancel(true)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            mBuilder.setChannelId(CHANNEL_ID)
            mNotificationManager?.createNotificationChannel(channel)
        }
        val notification = mBuilder.build()
        mNotificationManager?.notify(idNotif, notification)
    }

    private fun getReleaseToday(onSucsess: (MutableList<Movie>) -> Unit) {
        main.getReleaseToday(getDateNow(), {
            onSucsess(it)
        }, {
            logD(it)
        })
    }


    @SuppressLint("SimpleDateFormat")
    private fun getDateNow(): String {
        val sdf = SimpleDateFormat(DATE_FORMAT)
        return sdf.format(Date())
    }

    fun setRepeatingAlarmRelease(context: Context, type: String, message: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)

        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)

        val calendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, HOUR_RELEASE)
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        val pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        logD("release hour")
        toast(context, TYPE_RELEASE)
    }

    fun setRepeatingAlarmDaily(context: Context, type: String, message: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)

        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)

        val calendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, HOUR_DAILY)
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);


        val pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        logD("release hour")
        toast(context, TYPE_RELEASE)
    }

    fun cancelAlarm(context: Context, type: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val intent = Intent(context, NotificationReceiver::class.java)
        val requestCode =
            if (type.equals(TYPE_DAILY, ignoreCase = true)) {
                toast(context, TYPE_DAILY)
                ID_DAILY
            } else {
                toast(context, TYPE_RELEASE)
                ID_RELEASE
            }

        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()
        alarmManager?.cancel(pendingIntent)

        Toast.makeText(context, "Repeating alarm dibatalkan", Toast.LENGTH_SHORT).show()
    }

}