package com.blueberrysolution.pinelib19.pop_up_windows.waitting_new

import android.app.Activity
import com.blueberrysolution.pinelib19.R
import com.blueberrysolution.pinelib19.activity.A

class Loading {
    var showing = false;



    fun show(activity: Activity = A.a()){
        if (!showing){
            showing = true;
            WaittingDialog.createLoadingDialog(activity, A.s(R.string.loading_word))
        }

    }

    fun hide(){
        showing = false;
        WaittingDialog.closeDialog()
    }

    fun isShowing(): Boolean{
        return showing;
    }

    companion object{
        private var loading: Loading? = null;
        fun i(): Loading{
            if (loading == null){
                loading = Loading();
            }
            return loading!!;
        }
    }
}