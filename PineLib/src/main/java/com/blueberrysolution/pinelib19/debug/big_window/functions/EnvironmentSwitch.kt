package com.blueberrysolution.pinelib19.debug.big_window.functions

import com.blueberrysolution.pinelib19.R
import com.blueberrysolution.pinelib19.activity.A
import com.blueberrysolution.pinelib19.activity.t.T
import com.blueberrysolution.pinelib19.addone.broadcast.Broadcast
import com.blueberrysolution.pinelib19.addone.broadcast.OnBroadcast
import com.blueberrysolution.pinelib19.addone.restart.Restart
import com.blueberrysolution.pinelib19.addone.share_preferences.Sp
import com.blueberrysolution.pinelib19.debug.big_window.function.DebugFunctions
import com.blueberrysolution.pinelib19.pop_up_windows.msgbox.MessageBox
import com.blueberrysolution.pinelib19.pop_up_windows.msgbox.WindowsStyle

class EnvironmentSwitch : OnBroadcast {



    override fun onBroadcast(key: String, withObject: Any?) {
        if (key == "key_up_down"){
            Sp.i.put("force_live_environment", 0);
            Restart.restartApp3();
        }
    }


    companion object{

        var startCheckObj: EnvironmentSwitch? = null;


        //应用启动时候调用这个函数检查
        fun startCheck(){
            if (Sp.i.get("force_live_environment", 0) == 1){
                A.app().isDebug = false;
                startCheckObj = EnvironmentSwitch();
                Broadcast.i.reg("key_up_down", startCheckObj!!);

            }
        }


        fun add(){
            DebugFunctions.add(A.s(R.string.float_windows_environment_switch), ::switchEnvironment);
        }

        fun switchEnvironment(){
            MessageBox.i().setListener {
                if (it == 1){
                    A.app().onLiveInit();
                }
                else if (it == 2){
                    Sp.i.put("force_live_environment", 1);
                    Restart.restartApp3();
                }
            }.show(A.s(R.string.float_windows_environment_switch), A.s(R.string.float_windows_environment_switch_only_data), A.s(R.string.float_windows_environment_switch_total_switch), A.s(R.string.cancel_text))
        }

    }
}