package com.blueberrysolution.pinelib19.debug.big_window.functions

import com.blueberrysolution.pinelib19.R
import com.blueberrysolution.pinelib19.activity.A
import com.blueberrysolution.pinelib19.addone.restart.Restart
import com.blueberrysolution.pinelib19.debug.big_window.function.DebugFunctions
import com.blueberrysolution.pinelib19.pop_up_windows.msgbox.MessageBox
import com.blueberrysolution.pinelib19.pop_up_windows.msgbox.WindowsStyle

object KillApp{
    fun add(){
        DebugFunctions.add(A.s(R.string.float_windows_kill_app), ::killApp);
    }

    fun killApp(){
        Restart.exitApp()
    }
}