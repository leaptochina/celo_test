package com.blueberrysolution.pinelib19.pop_up_windows.waitting_new

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.view.LayoutInflater
import com.blueberrysolution.pinelib19.R


object WaittingDialog {

    var dialog: Dialog? = null;

    fun createLoadingDialog(context: Context, msg: String): Dialog {
        val inflater = LayoutInflater.from(context)
        val v = inflater.inflate(R.layout.dialog_loading_1, null)// 得到加载view
        val layout = v
            .findViewById(R.id.dialog_loading_view) as LinearLayout// 加载布局
        val tipTextView = v.findViewById(R.id.tipTextView) as TextView// 提示文字
        tipTextView.text = msg// 设置加载信息

        val loadingDialog = Dialog(context, R.style.MyDialogStyle)// 创建自定义样式dialog
        loadingDialog.setCancelable(true) // 是否可以按“返回键”消失
        loadingDialog.setCanceledOnTouchOutside(false) // 点击加载框以外的区域
        loadingDialog.setContentView(
            layout, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )// 设置布局
        /**
         * 将显示Dialog的方法封装在这里面
         */
        val window = loadingDialog.getWindow()!!
        val lp = window.getAttributes()
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.setGravity(Gravity.CENTER)
        window.setAttributes(lp)
        window.setWindowAnimations(R.style.PopWindowAnimStyle)
        loadingDialog.show()

        this.dialog = loadingDialog;

        return loadingDialog
    }

    /**
     * 关闭dialog
     *
     * @param mDialogUtils
     */
    fun closeDialog() {
        if (dialog != null && dialog!!.isShowing()) {
            dialog!!.dismiss()
        }
    }
}