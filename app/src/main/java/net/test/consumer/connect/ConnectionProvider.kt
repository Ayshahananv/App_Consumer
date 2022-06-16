package net.test.consumer.connect

import android.content.Context
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.soti.xtsocket.ipc.controllers.XTSocket
import net.soti.xtsocket.ipc.interfaces.Ipc
import net.soti.xtsocket.ipc.interfaces.IpcService
import net.soti.xtsocket.ipc.model.Request

class ConnectionProvider : IpcService {
    private val TAG = "Aysha"

    suspend fun connect(context: Context): Ipc? {
        return XTSocket.connect(context, "net.soti.smartbattery.zebra")
    }

    fun fetchData(ipc: Ipc) {
        GlobalScope.launch {
            GlobalScope.launch {
                Log.d(TAG, "fetchData: first request")
                val data1 = ipc.requestData(
                    listOf(
                        Request("ratedCap"),
                        Request("cycCnt"),
                        Request("curChrg")
                    )
                )
                Log.d(TAG, "fetchData: data from first request: $data1")
            }
            Log.d(TAG, "fetchData: second request")
            val data2 =
                ipc.requestData(listOf(Request("ratedCap"), Request("cycCnt"), Request("curChrg")))
            Log.d(TAG, "fetchData: data from second request: $data2")
        }
    }

    override fun onDisconnected(packageName: String) {
        Log.d(TAG, "onDisconnected: ")
    }

    override fun onConnected(packageName: String, ipc: Ipc) {
/*        Log.d(TAG, "onConnected:")
        getSchema(ipc)
        fetchData(ipc)
        Scheduler().setAlarm(this.contextH, Scheduler.getTime())*/
    }
}

/////////////////////////////////////////////////////////////////////////////////////////////
/*        val data = ipc.requestData(
            listOf(
                Request("model", JSONObject().toString()),
                Request("version", JSONObject().toString()),
                Request("ip", JSONObject().toString()),
                Request("manufacturer", JSONObject().toString()),
                Request("abc", JSONObject().toString())
            )
        )
        Log.d(TAG, "fetchData: ${data?.toJSON()} ")*/

/*    private fun getSchema(ipc: Ipc) {
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
            //Log.d(TAG, "getSchema:${ipc.init(pkg,params)} ")
        }
    }*/

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
