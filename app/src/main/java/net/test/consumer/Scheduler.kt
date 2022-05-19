package net.test.consumer

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.util.Log
import java.util.*

class Scheduler {
    private val TAG = "ONE_C"

    companion object {
        fun getTime(): Long {
            Log.d("ONE_C", "getTime:4 ")
            val calendar: Calendar = Calendar.getInstance()
            calendar.set(Calendar.MINUTE, 25)
            calendar.set(Calendar.SECOND, 1)
            calendar.set(Calendar.MILLISECOND, 10)
            return calendar.timeInMillis
        }
    }

    fun setAlarm(context: Context, time: Long) {
        Log.d(TAG, "setAlarm: 4 (5)")
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent)
    }
}