package com.blueberrysolution.pinelib19.debug.big_window.functions



import android.app.ActivityManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.blueberrysolution.pinelib19.R
import com.blueberrysolution.pinelib19.activity.A
import com.blueberrysolution.pinelib19.debug.big_window.function.DebugFunctions
import com.blueberrysolution.pinelib19.pop_up_windows.msgbox.MessageBox
import com.blueberrysolution.pinelib19.pop_up_windows.msgbox.WindowsStyle

object ResetApplication{
    fun add(){
        DebugFunctions.add(A.s(R.string.float_windows_reset), ::resetApp);
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun resetApp(){
        MessageBox.i().setListener{ choosedId ->
            if (choosedId == 2){
                var am = A.c().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                var res = am.clearApplicationUserData();
            }
        }
            .setCancelable(true)
            .setStyle(WindowsStyle.WhiteMiddle)
            .show(A.s(R.string.float_windows_msgbox_do_you_want), "No", "Yes")



    }



}