package com.blueberrysolution.pinelib19.activity.t

import android.app.Activity
import android.widget.Toast
import com.blueberrysolution.pinelib19.activity.A
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


fun Activity.t(message : String) {
    T.t(message);
}

class T(){

    companion object  {


        //强制主线程
        fun t(message: String) {
            doAsync {
                uiThread {
                    Toast.makeText(A.a(), message, Toast.LENGTH_SHORT).show();
                }
            }
        }



    }
}