package com.blueberrysolution.pinelib19.addone.mytimer_new

import android.os.Handler
import android.os.Looper
import com.blueberrysolution.pinelib19.addone.mytimer.MyTimer
import com.blueberrysolution.pinelib19.addone.mytimer.OnTimerListener
import java.lang.ref.WeakReference
import java.util.*
import kotlin.concurrent.thread
import com.blueberrysolution.pinelib19.addone.mytimer.MyTimerListenerInstance as MyTimerListenerInstance1


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class MyTimerN {
    var interval: Long = 1000;
    var stopImmediate: Boolean = false;
    var allowRun: Boolean = false;

    var onTimerUIListener: (() -> Unit)? = null;
    var onTimerThreadListener: (() -> Unit)? = null;

    var mHandler = Handler(Looper.getMainLooper())

    var r: Runnable = object : Runnable {
        override fun run() {
            //do something
            if (!stopImmediate){
                thread {
                    if (onTimerThreadListener != null)
                        onTimerThreadListener!!();
                }
                if (onTimerUIListener != null)
                    onTimerUIListener!!();
                //每隔1s循环执行run方法
                if (allowRun){
                    mHandler.postDelayed(this, interval)
                }else{
                    stopImmediate = true;
                }
            }


        }
    }

    fun stop(){
        stopImmediate = true;

    }

    fun start(): MyTimerN{
        stopImmediate = false;
        allowRun = true;

        mHandler.postDelayed(r, interval)

        return this;
    }

    fun startOnce(): MyTimerN{
        stopImmediate = false;
        allowRun = false;


        mHandler.postDelayed(r, interval)

        return this;
    }

    fun setOnTimerUIListener(callback: () -> Unit): MyTimerN {
        this.onTimerUIListener = callback;
        return  this;
    }

    fun setOnTimerThreadListener(callback: () -> Unit): MyTimerN {
        this.onTimerThreadListener = callback;
        return  this;
    }

    fun setInterval(interval: Long): MyTimerN {
        if (interval > 0){
            this.interval = interval;
        }
        return  this;
    }

    companion object {
        fun i(): MyTimerN {
            return MyTimerN();
        }
    }

}
