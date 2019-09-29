package com.example.moviecatalogue

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.moviecatalogue.notification.NotificationReceiver
import kotlinx.android.synthetic.main.activity_notification_settings.*

class NotificationSettingsActivity : AppCompatActivity() {

    private val main = MainApp()
    private lateinit var notificationReceiver: NotificationReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_settings)

        stateSetting()

        notificationReceiver = NotificationReceiver()

        switchDaily.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                main.putStringSharePref(MainApp.DAILY_REMINDER, MainApp.DAILY_REMINDER, this)
                notificationReceiver.setRepeatingAlarmDaily(
                    this,
                    NotificationReceiver.TYPE_DAILY,
                    NotificationReceiver.DAILY_MESSAGE
                )
            } else {
                main.removeStringSharePref(MainApp.DAILY_REMINDER, this)
                notificationReceiver.cancelAlarm(this, NotificationReceiver.TYPE_DAILY)
            }
        }
        switchRelease.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                main.putStringSharePref(MainApp.REALEASE_REMINDER, MainApp.REALEASE_REMINDER, this)
                notificationReceiver.setRepeatingAlarmRelease(
                    this,
                    NotificationReceiver.TYPE_RELEASE,
                    NotificationReceiver.RELEASET_TODAY
                )
            } else {
                main.removeStringSharePref(MainApp.REALEASE_REMINDER, this)
                notificationReceiver.cancelAlarm(this, NotificationReceiver.TYPE_RELEASE)
            }
        }
    }

    private fun stateSetting() {
        switchDaily.isChecked = main.getStringSharePref(MainApp.DAILY_REMINDER, this) != ""
        switchRelease.isChecked = main.getStringSharePref(MainApp.REALEASE_REMINDER, this) != ""
    }

}
