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
    val pkg = "com.testing.app_consumer"

    companion object {
        var connected = false
        private lateinit var iRequest: IRequest
    }

    var mfd = ""
    var serialnumber = ""
    var partnum = ""
    var ratedcapacity = ""
    var battery_decommission = ""
    var total_cumulative_charge = ""

    //ppp
    var base_cumulative_charge = ""
    var seconds_since_first_use = ""
    var present_capacity = ""
    var health_percentage = ""
    var time_to_empty = ""
    var time_to_full = ""
    var present_charge = ""

    //pp
    var battery_usage_numb = ""


    override fun onConnected() {
        Log.d(TAG, "onConnected: ")
        ////stop event
        // XTSConnection.getIpc().registerEventReceiver(pkg, EventReceiver())
        iRequest = XTSConnection.getIpc().iRequest()!!
        getSchema()
        getAPIs()
    }

    fun getSchema() {
        if (connected) {
            val schema = iRequest.schema
            Log.e(TAG, "onSchema: ${JSONObject(schema!!)}")
            val requiredFeature = JSONObject(schema).keys().asSequence().toMutableList()
            Log.d("kajal", "getSchema-required feature: $requiredFeature")
            iRequest.subscribe(pkg, requiredFeature)
            val mfdData =
                iRequest.requestData(pkg, listOf(Request("mfd ", JSONObject().toString())))
            Log.d("kajal", "getSchema:$mfdData ")
        }
    }

    fun getAPIs() {
        for (i in 0..500000) {
            mfd = iRequest.requestData(
                pkg, listOf(Request("mfd", JSONObject().toString()))
            ).toString()
            Log.d("kajal", "$i mfd:$mfd")

            serialnumber= iRequest.requestData(
                pkg, listOf(Request("serialnumber", JSONObject().toString()))
            ).toString()
            Log.d("kajal", "$i serialnumber:$serialnumber")

            partnum= iRequest.requestData(
                pkg, listOf(Request("partnum", JSONObject().toString()))
            ).toString()
            Log.d("kajal", "$i partnum:$partnum")

            ratedcapacity= iRequest.requestData(
                pkg, listOf(Request("ratedcapacity", JSONObject().toString()))
            ).toString()
            Log.d("kajal", "$i ratedcapacity:$ratedcapacity")

            battery_decommission= iRequest.requestData(
                pkg, listOf(Request("battery_decommission", JSONObject().toString()))
            ).toString()
            Log.d("kajal", "$i battery_decommission:$battery_decommission")

            total_cumulative_charge= iRequest.requestData(
                pkg, listOf(Request("total_cumulative_charge", JSONObject().toString()))
            ).toString()
            Log.d("kajal", "$i total_cumulative_charge:$total_cumulative_charge")

            //ppp
            base_cumulative_charge= iRequest.requestData(
                pkg, listOf(Request("base_cumulative_charge", JSONObject().toString()))
            ).toString()
            Log.d("kajal", "$i base_cumulative_charge:$base_cumulative_charge")

            seconds_since_first_use= iRequest.requestData(
                pkg, listOf(Request("seconds_since_first_use", JSONObject().toString()))
            ).toString()
            Log.d("kajal", "$i seconds_since_first_use:$seconds_since_first_use")

            present_capacity= iRequest.requestData(
                pkg, listOf(Request("present_capacity", JSONObject().toString()))
            ).toString()
            Log.d("kajal", "$i present_capacity:$present_capacity")

            health_percentage= iRequest.requestData(
                pkg, listOf(Request("health_percentage", JSONObject().toString()))
            ).toString()
            Log.d("kajal", "$i health_percentage:$health_percentage")

            time_to_empty= iRequest.requestData(
                pkg, listOf(Request("time_to_empty", JSONObject().toString()))
            ).toString()
            Log.d("kajal", "$i time_to_empty:$time_to_empty")

            time_to_full= iRequest.requestData(
                pkg, listOf(Request("time_to_full", JSONObject().toString()))
            ).toString()
            Log.d("kajal", "$i time_to_full:$time_to_full")

            present_charge= iRequest.requestData(
                pkg, listOf(Request("present_charge", JSONObject().toString()))
            ).toString()
            Log.d("kajal", "$i present_charge:$present_charge")
            //pp
          // battery_usage_numb
        }
    }

//    fun getMfd() {
//        var mfd=""
//        for(i in 0..40000)  {
//            mfd = iRequest.requestData(
//                pkg, listOf(Request("mfd", JSONObject().toString()))
//            ).toString()
//            Log.d("kajal", "$i mfd:$mfd")
//        }
//    }

    override fun onDisconnected() {
        Log.d("kajal", "onDisconnected: ")
    }

    fun connect(context: Context) {
        connected = XTSConnection.getIpc().connect(context, "com.smartbattery.zebra", this)
        Log.d("kajal connected", connected.toString())
    }
}
//            val requiredFeature =
//                JSONObject(JSONObject(schema).get("schema").toString()).keys().asSequence()
//                    .toMutableList()
//iRequest.subscribe(pkg, requiredFeature)