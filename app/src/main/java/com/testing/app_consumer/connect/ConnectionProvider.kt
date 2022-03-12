package com.testing.app_consumer.connect

import android.content.Context
import android.util.Log
import android.view.View
import net.soti.xtsocket.ipc.IRequest
import net.soti.xtsocket.ipc.IResponse
import net.soti.xtsocket.ipc.controllers.TransportService
import net.soti.xtsocket.ipc.model.Request
import org.json.JSONObject
import java.lang.NullPointerException

class ConnectionProvider:TransportService() {
    private  val TAG = "Aysha"
    val pkg= "com.testing.app_consumer"
    var connected=false
    private lateinit var iRequest:IRequest
    var manufacturer:String=""
    var model:String=""
    var ip:String=""
    var wifi:String=""

    override fun onConnected() {
        Log.d(TAG, "onConnected: ")

        iRequest= iRequest()!!
        //for (i in 1..100000) {
        getSchema()

        //}
//        val s:String ="start_time"
//        val e:String="end_time"
//        val st:String="1636976059940"
//        val et:String="1636976060976"
        if (connected){
            manufacturer=getIRequest().requestData(
                pkg, arrayListOf(Request("manufacturer", JSONObject().toString()))
            ).toString()
            model=getIRequest().requestData(
                pkg, arrayListOf(Request("model", JSONObject().toString()))
            ).toString()
            ip=getIRequest().requestData(
                pkg, arrayListOf(Request("ip", JSONObject().toString()))
            ).toString()
            //wifi=getIRequest().requestData(pkg, arrayListOf(Request("wifi_status",JSONObject().toString()))).toString()
        }
       registerEventReceiver(pkg, EventReceiver())

        Log.d("Aysha manufacturer", manufacturer)
        Log.d("Aysha model", model)
        Log.d("Aysha ip", ip)
       //Log.d("Aysha wifi", wifi)
    }
    fun getIRequest():IRequest{
        return iRequest
    }
    fun getIRequest():IRequest{
        return iRequest
    }

    fun getSchema() {
            if (connected) {
                val schema = getIRequest().schema
                Log.d("Aysha schema", "onSchema: ${JSONObject(schema!!)}")
                val requiredFeature = JSONObject(schema).keys().asSequence().toMutableList()
                Log.d("aysha", "getSchema: required: $requiredFeature")
                //subscribe
                getIRequest().subscribe(pkg, requiredFeature)
            }
    }
//    fun getSchema() {
//        if (connected) {
//            val schema = getIRequest().schema
//            Log.d("kajal", "getSchema:$schema ")
//            Log.e(TAG, "onSchema: ${JSONObject(schema!!)}")
////            for testing only
//            val requiredFeature =
//                JSONObject(JSONObject(schema).get("schema").toString()).keys().asSequence()
//                    .toMutableList()
//            //requiredFeature.remove("wifi_status")
//            getIRequest().subscribe(pkg, requiredFeature)
//           // onSchemaReceived(true)
//        }
//    }
    override fun onDisconnected() {
        Log.d("Aysha disconnect", "onDisconnected: ")


    }
    fun connect(context:Context){
        Log.d("Aysha", "connect: Started")
        connected=connectProvider(context,"com.testing.app_producer",this)
        Log.d("Aysha connected", connected.toString())
    }
    fun s(){
        Log.d(TAG, "s: ")
    }
    fun ss(){
        TODO()
    }
}
class EventReceiver:IResponse.Stub(){
    override fun onEvent(p0: String?) {
        Log.d("Aysha", "onEvent: ${JSONObject(p0!!)}")
    }
}