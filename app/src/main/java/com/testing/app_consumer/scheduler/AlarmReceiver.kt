package com.testing.app_consumer.scheduler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.testing.app_consumer.connect.ConnectionProvider

class AlarmReceiver : BroadcastReceiver() {
    private val TAG = "kajal"

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive:AlarmReceiver ")
//        ConnectionProvider().getSchema()
        ConnectionProvider().getMfd()
    }
}