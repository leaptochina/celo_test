package com.blueberrysolution.pinelib19.debug.big_window.functions

import com.blueberrysolution.pinelib19.R
import com.blueberrysolution.pinelib19.activity.A
import com.blueberrysolution.pinelib19.debug.big_window.function.DebugFunctions

object Functions{
    var hasBeanInited = false;

    fun initAll(){

        DebugFunctions.clear();



        KillApp.add();

        ResetApplication.add();
        
        LanguageChange.add();

        EnvironmentSwitch.add();








        hasBeanInited = true;

    }
}