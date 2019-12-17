package com.blueberrysolution.pinelib19.debug

import android.util.Log
import com.blueberrysolution.pinelib19.R
import com.blueberrysolution.pinelib19.activity.A
import com.blueberrysolution.pinelib19.activity.BaseApp
import com.blueberrysolution.pinelib19.activity.t.T

object G{

    var tag = "Pine";
    var logList: ArrayList<LogObject> = ArrayList();
    var needUpdate = false;
    //var logs: StringBuilder = StringBuilder("");



    init{

    }

    fun add(type: String, detail: String): String{
        var logObject: LogObject;
        if (logList.count() >= 80){
            logObject = logList.removeAt(0)
        }
        else{
            logObject = LogObject();
        }



        logObject.color = when {
            type.equals("d") -> R.color.white
            type.equals("i") -> R.color.lightgreen
            type.equals("w") -> R.color.lightblue
            type.equals("e") -> R.color.pink
            else -> R.color.white
        }
        logObject.detail = detail;
        logList.add(logObject);

        if (!needUpdate)
            needUpdate = true;

        if(BaseApp.i().realtimeLogShow){
            if (A.activity != null){
                DebugWindows.checkAndUpdateLogs();
            }

        }
//        logs.insert(0, "<font color='${color}'>${detail}</font><br>");

        return  detail;
    }

    fun d(s: String){
        if (BaseApp.i().isDebug){

            var l = add("d", s);
            Log.d(tag, l);
        }


    }

    fun i(s: String){
        if (BaseApp.i().isDebug) {
            var l = add("i", s);
            Log.i(tag, l);
        }
    }

    fun w(s: String){
        var l =  add("w", s);
        Log.w(tag, l);

    }

    fun e(s: String){
        var l =  add("e", s);
        Log.e(tag, l);
        T.t(s)
    }
}