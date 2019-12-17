package com.blueberrysolution.pinelib19.addone.gson

import android.view.View
import com.blueberrysolution.pinelib19.addone.inject_replace.MyViewHolder
import com.blueberrysolution.pinelib19.addone.serializable.SerializableClass
import com.blueberrysolution.pinelib19.debug.G
import com.blueberrysolution.pinelib19.net.entry.N
import com.google.gson.Gson
import java.lang.Exception
import java.lang.reflect.Type
import kotlin.reflect.KClass

/**
 *
fun refreshData(){
MyGson(BusInfo::class).fromJson(Configs.url_bus_detail_location_only + routeId, ::onBusLocationResult)
}

fun onBusResult(response: BusInfo){

}
 */
class MyGson<T : SerializableClass>{

    var url = "";
    val gson: Gson = Gson();
    lateinit var callback:  (retObj: T) -> Unit;
    var onError: (e: Exception) -> Unit = ::onNoCatchError
    lateinit var type: KClass<T>;

    constructor(type: KClass<T>){
        this.type = type;
    }

    fun  fromJson(url: String, callback: (retObj: T) -> Unit){
        fromJson(url, callback, ::onNoCatchError)
    }

    fun  fromJson(url: String, callback: (retObj: T) -> Unit, onError: (e: Exception) -> Unit = ::onNoCatchError){
        N.i.getSourceCode(url, ::onJsonResult, onError)
        this.url = url;
        this.callback = callback;
        this.onError = onError
    }

    fun onJsonResult(response: String){
        var t: T;
        try {
            t = gson.fromJson<T>(response, type.java)

        }
        catch (e: Exception){
            G.w("JsonConvert Err: " + response)
            onError(e);
            return;
        }
        callback(t)

    }

    fun onNetError(e: Exception){
        G.w("Network Error: " + e.toString())
        onError(e);

    }

    fun onNoCatchError(e: Exception){
        G.w("Error Happened during access " + url)
    }

}