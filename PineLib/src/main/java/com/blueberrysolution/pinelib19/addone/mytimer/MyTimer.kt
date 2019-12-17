package com.blueberrysolution.pinelib19.addone.mytimer

import android.os.Handler
import android.os.Looper
import java.lang.ref.WeakReference
import java.util.*
import com.blueberrysolution.pinelib19.addone.mytimer.MyTimerListenerInstance as MyTimerListenerInstance1


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class MyTimer {

    var onTimerListener: WeakReference<OnTimerListener>? = null;
    var interval: Long = 1000;
    var timer: Timer? = null;
    var isRunning: Boolean = false;
    var callbackInThread: Boolean? = null;
    var callbackInMainThread: Boolean? = false;
    var mHandler = Handler(Looper.getMainLooper()) {
        onTimerListener?.get()?.onTimer();
        true;
    }

    var task: TimerTask? = object : TimerTask() {
        override fun run() {

            if (callbackInMainThread!!){
                mHandler.obtainMessage(0, null).sendToTarget()
            }
            else if (callbackInThread == null){
                onTimerListener?.get()?.onTimer();
            }
            else{
                onTimerListener?.get()?.onTimer();



            }

        }
    };





    fun stop(){
        isRunning = false;
        timer?.cancel();
    }

    fun runInThread(isRunInThread: Boolean = true): MyTimer{
        this.callbackInThread = isRunInThread;

        return this;
    }

    fun setCallbackInMainThread(callbackInMainThread: Boolean): MyTimer{
        this.callbackInMainThread = callbackInMainThread;
        return  this;
    }

    fun start(): MyTimer{
        isRunning = true;
        timer = Timer(true);
        timer?.schedule(task, interval, interval); // 延时1000ms后执行，1000ms执行一次

        return this;
    }

    fun startOnce(): MyTimer{
        isRunning = true;
        timer = Timer(true);
        timer?.schedule(task, interval); // 延时1000ms后执行，1000ms执行一次

        return this;
    }

    fun setOnTimerListener(onTimerListener: OnTimerListener): MyTimer{
        this.onTimerListener = WeakReference(onTimerListener);

        return  this;
    }
    fun setOnTimerListener(callback: () -> Unit): MyTimer{
        this.onTimerListener = WeakReference(MyTimerListenerInstance1(callback))

        return  this;
    }
    



    fun setInterval(interval: Long): MyTimer {
        if (interval > 0){
            this.interval = interval;
        }
        return  this;
    }


    companion object {
        fun i(): MyTimer {
            return MyTimer();
        }
    }

}
