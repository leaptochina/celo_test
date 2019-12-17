package com.blueberrysolution.pinelib19.addone.thread

import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat.startActivity



object ThreadSwitch{

    fun delayRun(millionSecond: Long, runWhat:  () -> Unit){
        var mHandler = Handler();
        class SplashHandler : Runnable {
            override fun run() {
                runWhat();
            }
        }

        mHandler.postDelayed(SplashHandler(), millionSecond)
    }

    fun uiThread(uiThreadRun: () -> Unit, afterRun: (() -> Unit)? = null){
        val mainHandler = Handler(){
            if (afterRun != null)
                afterRun();
            true;
        }

        var mHandler = Handler(Looper.getMainLooper()) {
            uiThreadRun();
            mainHandler.obtainMessage(0, null).sendToTarget()
            true;
        }

        mHandler.obtainMessage(0, null).sendToTarget()
    }

    fun uiThread(uiThreadRun: () -> Unit){
        uiThread(uiThreadRun, null);
    }



}