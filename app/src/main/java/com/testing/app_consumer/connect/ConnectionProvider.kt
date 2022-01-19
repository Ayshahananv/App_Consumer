package com.testing.app_consumer.connect

import android.content.Context
import android.util.Log
import net.soti.xtsocket.ipc.IRequest
import net.soti.xtsocket.ipc.controllers.XTSConnection
import net.soti.xtsocket.ipc.interfaces.IpcService
import net.soti.xtsocket.ipc.model.Request
import org.json.JSONObject

class ConnectionProvider : IpcService {
    private val TAG = "kajal"

    companion object {
        val pkg = "com.testing.app_consumer"
        var connected = false
        lateinit var iRequest: IRequest
    }

    override fun onConnected() {
        Log.d(TAG, "onConnected: ")
        iRequest = XTSConnection.getIpc().iRequest()!!
//        XTSConnection.getIpc().registerEventReceiver(pkg, EventReceiver)
        if (connected) {
            getSchema()
            getPanasonicAPIs()
        }
    }

    override fun onDisconnected() {
        Log.d(TAG, "onDisconnected: ")
    }

    fun connect(context: Context) {
        connected = XTSConnection.getIpc().connect(context, "com.smartbattery.panasonic", this)
//        connected = XTSConnection.getIpc().connect(context, "com.testing.app_producer", this)
        Log.d(TAG, "connect: $connected")
    }

    fun getSchema() {
        if (connected) {
            val schema = iRequest.schema
            Log.e(TAG, "onSchema: ${JSONObject(schema!!)}")
//            val requiredFeature = JSONObject(schema).keys().asSequence().toMutableList()
            val requiredFeature =
                JSONObject(JSONObject(schema).get("schema").toString()).keys().asSequence()
                    .toMutableList()
            Log.d(TAG, "getSchema:$requiredFeature ")
            iRequest.subscribe(pkg, requiredFeature)
        }
    }

    fun getPanasonicAPIs() {
        val serial = iRequest.requestData(
            pkg, listOf(Request("serial", JSONObject().toString()))
        ).toString()
        Log.d("kajal", serial)

        val product_date = iRequest.requestData(
            pkg, listOf(Request("product_date", JSONObject().toString()))
        ).toString()
        Log.d("kajal", product_date)

        val health = iRequest.requestData(
            pkg, listOf(Request("health", JSONObject().toString()))
        ).toString()
        Log.d("kajal", health)

        val count = iRequest.requestData(
            pkg, listOf(Request("count", JSONObject().toString()))
        ).toString()
        Log.d("kajal", count)
    }
}

