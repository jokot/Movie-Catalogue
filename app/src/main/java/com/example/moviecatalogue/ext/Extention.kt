package com.example.moviecatalogue.ext

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.moviecatalogue.MainApp

fun toast(context: Activity, message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, duration).show()
}

fun toast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, duration).show()
}

fun String.toast(context: Activity, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, duration).show()
}

fun String.toast(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, duration).show()
}

fun logD(message: String) {
    Log.d(MainApp.LOG_D, message)
}
