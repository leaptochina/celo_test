package com.blueberrysolution.pinelib19.debug.window

import com.blueberrysolution.pinelib19.activity.A
import com.blueberrysolution.pinelib19.activity.I
import com.blueberrysolution.pinelib19.addone.share_preferences.Sp
import com.blueberrysolution.pinelib19.permission.RequireFloatWindow

class DebugWindowsController{
    var isInited = false;
    var isLastTimeDisableDebugWindow = false;
    var debugWindowSmall:DebugWindowSmall? = null;


    init{
        reInit();
    }

    fun reInit(){

        if (!A.app().isDebug)  return;

        isInited = false;
        isLastTimeDisableDebugWindow = Sp.i.get("isLastTimeDisableDebugWindow", false);

        if (isLastTimeDisableDebugWindow) return;

        CloseOldWindow();
        CheckFloatWindowPremission();

        ShowFloatWindow();
    }

    fun CloseOldWindow(){
        if (debugWindowSmall != null){
            if (debugWindowSmall!!.isShowing){
                debugWindowSmall!!.hide();
            }
        }
    }

    fun CheckFloatWindowPremission(){
        if (!RequireFloatWindow.isAllow()){
            I.i(DebugWindowRequirePermission::class).show();
        }

    }

    fun ShowFloatWindow(){
        if (RequireFloatWindow.isAllow()){
            debugWindowSmall = DebugWindowSmall();
            debugWindowSmall!!.show();

        }
    }











    companion object {
        var debugWindowsController: DebugWindowsController? = null;

        fun i(): DebugWindowsController {
            if (debugWindowsController == null){
                debugWindowsController = DebugWindowsController();
            }
            return debugWindowsController!!
        }
    }


}