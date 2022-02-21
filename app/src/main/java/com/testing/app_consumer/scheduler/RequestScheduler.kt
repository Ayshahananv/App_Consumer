package com.testing.app_consumer.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class RequestScheduler {
    private val TAG = "kajal"

    companion object {
        fun getTime(): Long {
            val calendar = Calendar.getInstance()
//            calendar.set(Calendar.MINUTE, 24)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            Log.d(TAG, "getTime: $calendar")
            return calendar.timeInMillis
        }
    }

    fun setAlarm(context: Context, time: Long) {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS",Locale.getDefault())
        val dateString = simpleDateFormat.format(getTime())
        Log.d(TAG, "setAlarm $dateString")

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent)
    }
}