package com.testing.app_consumer.connect

import android.content.Context
import android.util.Log
import com.testing.app_consumer.receiver.EventReceiver
import net.soti.xtsocket.ipc.IRequest
import net.soti.xtsocket.ipc.controllers.XTSConnection
import net.soti.xtsocket.ipc.interfaces.IpcService
import net.soti.xtsocket.ipc.model.Request
import org.json.JSONObject

class ConnectionProvider : IpcService {
    private val TAG = "kajal"
    val pkg = "com.testing.app_consumer"
    var connected = false
    private lateinit var iRequest: IRequest
    var manufacturer: String = ""
    var model: String = ""
    var ip: String = ""
    var wifi: String = ""

    override fun onConnected() {
        Log.d(TAG, "onConnected: ")
        XTSConnection.getIpc().registerEventReceiver(pkg, EventReceiver())
        iRequest = XTSConnection.getIpc().iRequest()!!
        getSchema()
        getMfd()
        if (connected) {
//           val mfd = iRequest.requestData(
//                pkg, listOf(Request("manufacturer", JSONObject().toString()))
//            )
//        Log.d("kajal", mfd)
//            model=getIRequest().requestData(
//                pkg, arrayListOf(Request("model", JSONObject().toString()))
//            ).toString()
//            ip=getIRequest().requestData(
//                pkg, arrayListOf(Request("ip", JSONObject().toString()))
//            ).toString()
            //wifi=getIRequest().requestData(pkg, arrayListOf(Request("wifi_status",JSONObject().toString()))).toString()
        }

//        Log.d("kajal model", model)
//        Log.d("kajal ip", ip)
        //Log.d("kajal wifi", wifi)
    }

    fun getSchema() {
        if (connected) {
            val schema = iRequest.schema
            Log.e(TAG, "onSchema: ${JSONObject(schema!!)}")
            val requiredFeature = JSONObject(schema).keys().asSequence().toMutableList()
//            val requiredFeature =
//                JSONObject(JSONObject(schema).get("schema").toString()).keys().asSequence()
//                    .toMutableList()
            Log.d(TAG, "getSchema:$requiredFeature ")
            iRequest.subscribe(pkg,requiredFeature)
            val mfdData=iRequest.requestData(pkg, listOf(Request("manufacturer",JSONObject().toString())))
            Log.d("kajal", "getSchema:$mfdData ")
        }
    }

    fun getMfd() {
        val mfd = iRequest.requestData(
            pkg, listOf(Request("manufacturer", JSONObject().toString()))
        ).toString()
        Log.d("kajal", mfd)
    }

    override fun onDisconnected() {
        Log.d("kajal", "onDisconnected: ")
    }

    fun connect(context: Context) {
        connected = XTSConnection.getIpc().connect(context, "com.testing.app_producer", this)
        Log.d("kajal connected", connected.toString())
    }
}

//    fun getSchema() {
//        if (connected) {
//            val schema = iRequest.schema
//            Log.d("kajal schema", "onSchema: ${JSONObject(schema!!)}")
//
//            val requiredFeature =
//                JSONObject(JSONObject(schema).get("schema").toString()).keys().asSequence()
//                    .toMutableList()
////                val requiredFeature = JSONObject(schema).keys().asSequence().toMutableList()
//            Log.d("kajal", "getSchema: required: $requiredFeature")
//            //subscribe
//                iRequest.subscribe(pkg, requiredFeature)
//            val mfd = iRequest.requestData(
//                pkg, listOf(Request("manufacturer", JSONObject().toString()))
//            )
//            Log.d("kajal", mfd)
//        }
//    }