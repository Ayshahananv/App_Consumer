package com.testing.app_consumer.connect

import android.content.Context
import android.util.Log
import net.soti.xtsocket.ipc.IRequest
import net.soti.xtsocket.ipc.controllers.XTSocket
import net.soti.xtsocket.ipc.interfaces.Ipc
import net.soti.xtsocket.ipc.interfaces.IpcService
import net.soti.xtsocket.ipc.model.Request
import org.json.JSONObject

class ConnectionProvider : IpcService {
    private val TAG = "Aysha"
    private val pkg = "com.testing.app_consumer"
    private var connected = false
    private lateinit var iRequest: IRequest
    var manufacturer: String = ""
    lateinit var ipc: Ipc

    override fun onConnected(packageName: String, ipc: Ipc) {
        this.ipc = ipc
        Log.d(TAG, "onConnected: ")
        iRequest = ipc.iRequest()!!
        getSchema()
        if (connected) {
            manufacturer = iRequest.requestData(
                pkg, listOf(Request("level", JSONObject().toString()))
            ).toString()
        }
        Log.d(TAG, "manu: $manufacturer")
    }

    private fun getSchema() {
        if (connected) {
            val schema = JSONObject(iRequest.schema!!.value!!)
            Log.e(TAG, "onSchema: ${JSONObject(iRequest.schema!!.value!!)}")
            val requiredFeature =
                schema.keys().asSequence().toList()
            iRequest.subscribe(pkg, requiredFeature)
        }
    }

    override fun onDisconnected() {
        Log.d(TAG, "onDisconnected: ")
    }

    fun connect(context: Context) {
        connected = XTSocket().connect(context, "com.zebra.smartbattery", this)
        Log.d(TAG,"connected${connected.toString()}")
    }
}