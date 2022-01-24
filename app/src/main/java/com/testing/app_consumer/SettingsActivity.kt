package com.testing.app_consumer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.testing.app_consumer.connect.ConnectionProvider

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        ConnectionProvider().connect(this)
    }

}