package com.blueberrysolution.pinelib19.hardware.vibrator

import android.app.Service
import android.content.Context.VIBRATOR_SERVICE
import android.os.Vibrator
import androidx.core.content.ContextCompat.getSystemService
import com.blueberrysolution.pinelib19.activity.A


object VibratorController {
    var vb: Vibrator? = null;

    fun getVibrate(): Vibrator{
        if (vb == null){
            vb = A.c().getSystemService(Service.VIBRATOR_SERVICE) as Vibrator?
        }
        return vb!!;

    }

    fun vibrate(milliseconds: Long){
        getVibrate().vibrate(milliseconds)
    }


}