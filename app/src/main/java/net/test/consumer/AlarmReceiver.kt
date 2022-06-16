package net.test.consumer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver : BroadcastReceiver() {
    private val TAG = "ONE_C"

    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.d(TAG, "Alarmreceiver ")
        ConnectionProvider.ipc?.disconnect()
       // ConnectionProvider().fetchData()
    }
}