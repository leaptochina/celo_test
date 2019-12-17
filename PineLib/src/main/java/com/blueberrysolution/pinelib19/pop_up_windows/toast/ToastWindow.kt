package com.blueberrysolution.pinelib19.pop_up_windows.toast

import android.view.View
import com.blueberrysolution.pinelib19.R
import com.blueberrysolution.pinelib19.activity.t.ClickToast

class ToastWindow: ClickToast(){
    override fun getLayout(): Int {
        return R.layout.msgbox_def
    }

    override fun setupView(view: View) {

    }

}