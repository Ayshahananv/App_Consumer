package com.testing.app_consumer.receiver

import android.util.Log
import net.soti.xtsocket.ipc.IResponse
import org.json.JSONObject

object EventReceiver : IResponse.Stub() {
    override fun onEvent(data: String?) {
        Log.d("kajal", "onEvent: ${JSONObject(data!!)}")
    }
}


