package com.blueberrysolution.pinelib19.addone.mytimer

class MyTimerListenerInstance(val callback: () -> Unit): OnTimerListener{
    override fun onTimer() {
        callback();
    }

}