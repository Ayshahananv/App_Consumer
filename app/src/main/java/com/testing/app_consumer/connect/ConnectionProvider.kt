package com.testing.app_consumer.connect

import android.content.Context
import android.util.Log
import com.testing.app_consumer.scheduler.RequestScheduler
//import com.testing.app_consumer.receiver.EventReceiver
import net.soti.xtsocket.ipc.IRequest
import net.soti.xtsocket.ipc.controllers.XTSocket
import net.soti.xtsocket.ipc.interfaces.Ipc
import net.soti.xtsocket.ipc.interfaces.IpcService
import net.soti.xtsocket.ipc.model.Request
import org.json.JSONObject

class ConnectionProvider : IpcService {
    private val TAG = "kajal"
    private lateinit var context2: Context

    companion object {
        val pkg = "com.testing.app_consumer"
        var connected = false
        lateinit var iRequest: IRequest
    }

    override fun onConnected(packageName: String, ipc: Ipc) {
        Log.d(TAG, "onConnected: ")
        iRequest = ipc.iRequest()!!
        // ipc.registerEventReceiver(pkg, EventReceiver)
        if (connected) {
            getSchema()
            RequestScheduler().setAlarm(context2, RequestScheduler.getTime())
          //  getMfd()
           // getPanasonicAPIs()
        }
    }

    override fun onDisconnected() {
        Log.d(TAG, "onDisconnected: ")
    }

    fun connect(context: Context) {
        context2 = context
//        connected = XTSocket().connect(context, "android.smartbattery.panasonic", this)
        connected = XTSocket().connect(context, "com.testing.app_producer", this)
        Log.d(TAG, "connect: $connected")
    }

    fun getSchema() {
        if (connected) {
            val schema = iRequest.schema.value
            Log.e(TAG, "onSchema: ${JSONObject(schema!!)}")
            val requiredFeature = JSONObject(schema).keys().asSequence().toMutableList()
//            requiredFeature.add("count2")
//            requiredFeature.add("remaining")
//            requiredFeature.add("serial2")
            requiredFeature.remove("#xtsConfig")
            Log.d(TAG, "getSchema:$requiredFeature ")
            iRequest.subscribe(pkg, requiredFeature)
        }
    }
    fun getMfd(){
                val mfd = iRequest.requestData(
            pkg, listOf(Request("manufacturer", JSONObject().toString()))
        ).toString()
        Log.d("kajal", mfd)
    }
//
//    fun getPanasonicAPIs() {
//
//        val serial = iRequest.requestData(
//            pkg, listOf(Request("serial", JSONObject().toString()))
//        ).toString()
//        Log.d("kajal", serial)
//
//        val productDate = iRequest.requestData(
//            pkg, listOf(Request("product_date", JSONObject().toString()))
//        ).toString()
//        Log.d("kajal", productDate)
//
//        val health = iRequest.requestData(
//            pkg, listOf(Request("health", JSONObject().toString()))
//        ).toString()
//        Log.d("kajal", health)
//
//        val count = iRequest.requestData(
//            pkg, listOf(Request("count", JSONObject().toString()))
//        ).toString()
//        Log.d("kajal", count)
//
//        val apis = iRequest.requestData(
//            pkg, listOf(
//                //    Request("manufacturer", JSONObject().toString()),
//                Request("serial", JSONObject().toString()),
//                Request("product_date", JSONObject().toString()),
//                Request("health", JSONObject().toString()),
//                Request("count", JSONObject().toString()),
//
//                Request("serial", JSONObject().toString()),
//                Request("product_date", JSONObject().toString()),
//                Request("health", JSONObject().toString()),
//                Request("count", JSONObject().toString()),
//
//                Request("serial", JSONObject().toString()),
//                Request("product_date", JSONObject().toString()),
//                //fz-a3
////                Request("remaining", JSONObject().toString()),
////                Request("serial2", JSONObject().toString()),
////                Request("product_date2", JSONObject().toString()),
////                Request("health2", JSONObject().toString()),
////                Request("count2", JSONObject().toString()),
////                Request("remaining", JSONObject().toString()),
//
//            )
//        ).toString()
//        Log.d("kajal", apis)
//
//    }
}

