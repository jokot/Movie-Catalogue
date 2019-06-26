package com.example.moviecatalogue

import android.content.Context
import android.util.Log
import android.widget.Toast

fun Context.toast(message: String, duration:Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message,duration).show()
}

fun Context.logD(message: String){
    Log.d(MainApp.LOG_D,message)
}