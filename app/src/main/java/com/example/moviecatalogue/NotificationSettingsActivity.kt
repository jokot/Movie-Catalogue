package com.example.moviecatalogue

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_notification_settings.*

class NotificationSettingsActivity : AppCompatActivity(){

    private val main = MainApp()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_settings)
        switchDaily.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                main.putStringSharePref(MainApp.DAILY_REMINDER,MainApp.DAILY_REMINDER,this)
            }else{
                main.putStringSharePref(MainApp.DAILY_REMINDER,MainApp.DAILY_REMINDER,this)
            }
        }
        switchRelease.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                main.putStringSharePref(MainApp.REALEASE_REMINDER,MainApp.REALEASE_REMINDER,this)
            }else{
                main.putStringSharePref(MainApp.REALEASE_REMINDER,MainApp.REALEASE_REMINDER,this)
            }
        }
    }

}
