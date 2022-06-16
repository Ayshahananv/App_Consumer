package net.test.consumer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.test.consumer.connect.ConnectionProvider
import org.json.JSONObject

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("ONE_C", "onCreate: Started ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        GlobalScope.launch {
            val ipc = ConnectionProvider().connect(Application.instance)
            val schema = JSONObject(ipc?.getSchema()!!.value!!)
            val features =schema.keys().asSequence().toMutableList()
            ipc.subscribe(features)
            ConnectionProvider().fetchData(ipc)
//            Log.d("ONE_C", "onCreate:${ipc.getInfo()}")
        }
    }
}