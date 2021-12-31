package com.testing.app_consumer

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import com.testing.app_consumer.connect.ConnectionProvider
import net.soti.xtsocket.ipc.controllers.TransportService
import kotlin.math.log

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Aysha", "onCreate: Started")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        ConnectionProvider().connect(this)
        Log.d("Aysha", "onCreate:ended ")
    }

}