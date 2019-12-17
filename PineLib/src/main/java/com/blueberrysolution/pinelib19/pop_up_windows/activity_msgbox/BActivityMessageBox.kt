package com.blueberrysolution.pinelib19.pop_up_windows.activity_msgbox

import android.content.Intent
import com.blueberrysolution.pinelib19.R
import com.blueberrysolution.pinelib19.activity.A
import com.blueberrysolution.pinelib19.pop_up_windows.msgbox.*
//有问题 没有写好 弃用
class BActivityMessageBox{

    private var style = WindowsStyle.BlackNoFrame
    private var listener: OnMessageClickListener? = null




    /**
     * 通过这里显示提示框
     *
     * @param message
     * @param buttons
     */
    @JvmOverloads
    fun show(message: String, vararg btns: String) {
        var btnsChecked = btns;
        if (btnsChecked.size == 0){
            btnsChecked = arrayOf(A.s(R.string.msgbox_def_btn))
        }
        showNow(message, btnsChecked)
    }

    fun showNow(message: String, btnsChecked: Array<out String>) {
        var intent = Intent(A.a(), ActivityMessageBox::class.java);
        intent.putExtra("style", style)
        intent.putExtra("title", message)
        intent.putExtra("btns", btnsChecked)
        A.a().startActivity(intent);

        if (listener != null){
            ActivityMessageBox.activityMassageBox?.setListener(listener!!)!!.setWindowsStyle(style);
        }


    }

    fun getStyle(): WindowsStyle {
        return style
    }


    fun setStyle(style: WindowsStyle): BActivityMessageBox {
        this.style = style
        return this
    }

    fun getListener(): OnMessageClickListener? {
        return listener
    }


    fun setListener(listener: OnMessageClickListener): BActivityMessageBox {
        this.listener = listener
        return this
    }

    fun setListener(func: (Int) -> Unit): BActivityMessageBox {
        this.listener = MessageBoxListener(func)
        return this
    }

    companion object {
        /**
         * 初始化
         *
         * <pre>
         * 必须将基础activity继承QFActivity/QFFragmentActivity
         * 才能用这个初始化
         * 否则 请传入上下文
        </pre> *
         *
         * @return
         */
        fun i(): BActivityMessageBox {

            return BActivityMessageBox();
        }



    }
}