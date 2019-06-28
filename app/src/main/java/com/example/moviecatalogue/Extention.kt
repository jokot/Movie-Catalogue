package com.example.moviecatalogue

import android.app.Activity
import android.util.Log
import android.widget.Toast

fun toast(context: Activity, message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, duration).show()
}

fun logD(message: String) {
    Log.d(MainApp.LOG_D, message)
}