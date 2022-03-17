package com.testing.app_consumer.connect

import android.content.Context
import android.util.Log
import net.soti.xtsocket.ipc.IRequest
import net.soti.xtsocket.ipc.controllers.ErrorHandler
import net.soti.xtsocket.ipc.controllers.XTSocket
import net.soti.xtsocket.ipc.interfaces.Ipc
import net.soti.xtsocket.ipc.interfaces.IpcService
import net.soti.xtsocket.ipc.model.Request
import net.soti.xtsocket.ipc.model.XTSResponse
import net.soti.xtsocket.ipc.model.toJSON
import net.soti.xtsocket.ipc.model.toXTSResponse
import org.json.JSONObject

class ConnectionProvider : IpcService {
    private val TAG = "ONE_C"
    private lateinit var contextH: Context

    companion object{
        val pkg = "com.testing.app_consumer"
        private var connected = false
    }


    override fun onConnected(packageName: String, ipc: Ipc) {
        Log.d(TAG, "onConnected:")
        getSchema(ipc)
        fetchData(ipc)
        //Scheduler().setAlarm(this.contextH, Scheduler.getTime())
    }

    fun fetchData(ipc: Ipc) {
        Log.d(TAG, "fetchData: ")
        val data = ipc.requestData(
            pkg, listOf(
                Request("model",JSONObject().toString()),
                Request("version", JSONObject().toString()),
                Request("ip", JSONObject().toString()),
                Request("manufacturer",JSONObject().toString()),
                Request("abc",JSONObject().toString())
                /*Request("uniqueId", JSONObject().toString()),
                Request("manufactureDate", JSONObject().toString()),
                Request("partNumber", JSONObject().toString()),
                Request("serialNumber", JSONObject().toString()),
                Request("newCapacity", JSONObject().toString()),
                Request("decommissionStatus", JSONObject().toString()),
                Request("totalCumulativeCharge", JSONObject().toString()),
                Request("baseCumulativeCharge", JSONObject().toString()),
                Request("secondsSinceFirstUse", JSONObject().toString()),
                Request("presentCapacity", JSONObject().toString()),
                Request("healthPercentage", JSONObject().toString()),
                Request("timeToEmpty", JSONObject().toString()),
                Request("timeToFull", JSONObject().toString()),
                Request("presentCharge", JSONObject().toString()),
                Request("batteryType", JSONObject().toString()),
                Request("isSmartBattery", JSONObject().toString())*/
            )
        )
        Log.d(TAG, "onConnected: ${data?.toJSON()} ")
    }

    private fun getSchema(ipc: Ipc) {
        Log.d(TAG, "getSchema: ")
        if (connected) {
            val schema = JSONObject(ipc.getSchema()!!.value!!)
            Log.e(TAG, "onSchema: ${JSONObject(ipc.getSchema()!!.value!!)}")
            val requiredFeature =
                schema.keys().asSequence().toMutableList()
            //requiredFeature.add("testingSub")
            Log.d(TAG, "getSchema: required: $requiredFeature")
            ipc.subscribe(pkg, requiredFeature)

           // Log.d("GETINFOO", "getInfo: ${JSONObject(ipc.getInfo(pkg)!!.value!!).getJSONObject("attributes").getJSONObject("values").get("version")}")
            Log.d("GETINFOO", "getInfo: ${JSONObject(ipc.getInfo(pkg, emptyList()/*listOf(
                Request("model",JSONObject().toString()),
                Request("version", JSONObject().toString()))*/)!!.value!!)}")

            val params = JSONObject()
            params.put("ratedCapacity", 4000)
            params.put("partnumber", "PT6690-BN:R .E")
            params.put("mfd", "OPPO")
            //Log.d(TAG, "getSchema:${ipc.init(pkg,params)} ")
        }
    }

    override fun onDisconnected(packageName: String) {
        Log.d(TAG, "onDisconnected: ")
    }

    fun connect(context: Context) {
        contextH = context
        Log.d(TAG, "connect:")
        connected = XTSocket().connect(context, "com.testing.app_producer", this)
        Log.d(TAG, "connected loop :${connected.toString()}")
    }
}