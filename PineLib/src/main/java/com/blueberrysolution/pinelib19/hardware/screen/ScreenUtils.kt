package com.blueberrysolution.pinelib19.hardware.screen

import android.view.WindowManager
import com.blueberrysolution.pinelib19.activity.A

object ScreenUtils{
    fun setKeepScreenOn(isOn: Boolean){
        if (isOn){
            A.a().getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        else{
            A.a().getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

    }
}