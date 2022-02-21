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
    private val TAG = "ONE_C"
    private lateinit var contextH: Context

    companion object{
        val pkg = "com.testing.app_consumer"
        private var connected = false
        lateinit var iRequest: IRequest
    }


    override fun onConnected(packageName: String, ipc: Ipc) {
        Log.d(TAG, "onConnected:")
        iRequest=ipc.iRequest()!!
        getSchema()
        //Scheduler().setAlarm(this.contextH, Scheduler.getTime())
    }

    fun fetchData() {
        Log.d(TAG, "fetchData: ")
        val data = iRequest.requestData(
            pkg, listOf(
                Request("mfd", JSONObject().toString()),
                Request("partnumber", JSONObject().toString()),
                Request("serialnumber", JSONObject().toString()),
                Request("ratedcapacity", JSONObject().toString()),
                Request("battery_decommission", JSONObject().toString()),
                Request("total_cumulative_charge", JSONObject().toString()),
                Request("base_cumulative_charge", JSONObject().toString()),
                Request("seconds_since_first_use", JSONObject().toString()),
                Request("present_capacity", JSONObject().toString()),
                Request("health_percentage", JSONObject().toString()),
                Request("time_to_empty", JSONObject().toString()),
                Request("time_to_full", JSONObject().toString()),
                Request("present_charge", JSONObject().toString()),
                //Request("battery_usage_numb", JSONObject().toString())
            )
        )
        Log.d(TAG, "onConnected: $data ")
    }

    private fun getSchema() {
        Log.d(TAG, "getSchema: ")
        if (connected) {
            val schema = JSONObject(iRequest.schema!!.value!!)
            Log.e(TAG, "onSchema: ${JSONObject(iRequest.schema!!.value!!)}")
            val requiredFeature =
                schema.keys().asSequence().toMutableList()
            //requiredFeature.remove("mfd")
            iRequest.subscribe(pkg, requiredFeature)
        }
    }

    override fun onDisconnected(packageName: String) {
        Log.d(TAG, "onDisconnected: ")
    }

    fun connect(context: Context) {
        contextH = context
        Log.d(TAG, "connect:")
        connected = XTSocket().connect(context, "com.zebra.smartbattery", this)
        Log.d(TAG, "connected loop :${connected.toString()}")
    }
}