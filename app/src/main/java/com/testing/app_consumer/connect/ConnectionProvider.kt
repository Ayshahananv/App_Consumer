package com.testing.app_consumer.connect

import android.content.Context
import android.util.Log
import com.testing.app_consumer.Scheduler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.soti.xtsocket.ipc.IRequest
import net.soti.xtsocket.ipc.controllers.ErrorHandler
import net.soti.xtsocket.ipc.controllers.XTSocket
import net.soti.xtsocket.ipc.interfaces.Ipc
import net.soti.xtsocket.ipc.interfaces.IpcService
import net.soti.xtsocket.ipc.model.Request
import net.soti.xtsocket.ipc.model.XTSResponse
import net.soti.xtsocket.ipc.model.toJSON
import net.soti.xtsocket.ipc.model.toXTSResponse
import net.soti.xtsocket.transform.database.XTSDatabase
import org.json.JSONObject
import kotlin.math.log

class ConnectionProvider : IpcService {
    private val TAG = "ONE_C"

    companion object {
        private var connected = false
        var ipc: Ipc? = null
    }


    override fun onConnected(packageName: String, ipc: Ipc) {
        Log.d(TAG, "onConnected:")
/*        getSchema(ipc)
        fetchData(ipc)*/
        //Scheduler().setAlarm(this.contextH, Scheduler.getTime())
    }

    fun fetchData(ipc: Ipc) {
        Log.d(TAG, "fetchData: ")
        val data = ipc.requestData(getRequestList())
        Log.d(TAG, "fetchData: ${data?.toJSON()} ")
    }

    private fun getSchema(ipc: Ipc) {
        Log.d(TAG, "getSchema: ")
        if (connected) {
            val schema = JSONObject(ipc.getSchema()!!.value!!)
            val requiredFeature = schema.keys().asSequence().toMutableList()
            ipc.subscribe(requiredFeature)

            // Log.d("GETINFOO", "getInfo: ${JSONObject(ipc.getInfo(pkg)!!.value!!).getJSONObject("attributes").getJSONObject("values").get("version")}")
            Log.d("GETINFOO", "getInfo: ${JSONObject(ipc.getInfo(emptyList())!!.value!!)}")

            val params = JSONObject()
            params.put("ratedCapacity", 4000)
            params.put("partnumber", "PT6690-BN:R .E")
            params.put("mfd", "OPPO")
        }
    }

    override fun onDisconnected(packageName: String) {
        Log.d(TAG, "onDisconnected: ")
    }

    suspend fun connect(context: Context): Ipc? {
        ipc = XTSocket.connect(context, "net.soti.smartbattery.zebra")
        //Scheduler().setAlarm(context, Scheduler.getTime())
//        ipc?.disconnect()
        return ipc
    }

    private fun getRequestList(): List<Request> {
        return listOf(
            Request("model", JSONObject().toString()),
            Request("version", JSONObject().toString()),
            Request("ip", JSONObject().toString()),
            Request("manufacturer", JSONObject().toString()),
            Request("abc", JSONObject().toString())
        )
    }
}


/*    fun connect(context: Context) {
        contextH = context
        Log.d(TAG, "connect:")
        val disc =XTSocket.discover(context)
        Log.e("AYSHa", "discover: ${XTSocket.discover(context,"term", "SB")}")
        Log.d("AYSHA","${XTSocket.getActiveSessions()}")
        for(each in disc){
            Log.d("AYSHA", "each: ${each}")
//            if(each.value.type=="SB")
//                connected = XTSocket().connect(context, "net.test.producer", this)
//            //Log.d("AYSHA", "connected loop :${connected.toString()}")
        }
    }*/