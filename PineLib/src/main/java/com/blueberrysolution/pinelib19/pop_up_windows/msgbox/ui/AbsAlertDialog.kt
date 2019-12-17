package com.blueberrysolution.pinelib19.pop_up_windows.msgbox.ui


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.ContextMenu
import android.view.View
import com.blueberrysolution.pinelib19.R
import com.blueberrysolution.pinelib19.activity.A


abstract class AbsAlertDialog : AlertDialog {


    abstract val view: AbsAlertDialog

    protected constructor(context: Context, theme: Int) : super(context, theme) {
        // TODO Auto-generated constructor stub
    }


    protected constructor(context: Context) : super(context) {
        // TODO Auto-generated constructor stub
    }


    protected constructor(
        context: Context, cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener
    ) : super(context, cancelable, cancelListener) {
        // TODO Auto-generated constructor stub
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //强制Dialog全屏
        val resources = A.c().getResources()
        val dm = resources.getDisplayMetrics()

        window!!.attributes.width = dm.widthPixels



    }
}
