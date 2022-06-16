package com.testing.app_consumer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.testing.app_consumer.connect.ConnectionProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.soti.xtsocket.ipc.model.Request
import org.json.JSONObject

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("ONE_C", "onCreate: Started ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        //ConnectionProvider().connect(this)
        GlobalScope.launch {
            val ipc = ConnectionProvider().connect(Application.instance)
            val schema = JSONObject(ipc?.getSchema()!!.value!!)
            val features =schema.keys().asSequence().toMutableList()
            Log.d("ONE_C", "onCreate: $schema")
            ipc.subscribe(features)
            val data = ipc.requestData(listOf(Request("id"), Request("partNm"), Request("ratedCap")))
        }
        //Scheduler().setAlarm(this, Scheduler.getTime())
    }

}