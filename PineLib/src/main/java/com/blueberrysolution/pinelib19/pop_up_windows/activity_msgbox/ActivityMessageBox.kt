package com.blueberrysolution.pinelib19.pop_up_windows.activity_msgbox

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.blueberrysolution.pinelib19.R
import com.blueberrysolution.pinelib19.activity.PineActivity
import com.blueberrysolution.pinelib19.pop_up_windows.msgbox.MessageBox
import com.blueberrysolution.pinelib19.pop_up_windows.msgbox.MessageBoxListener
import com.blueberrysolution.pinelib19.pop_up_windows.msgbox.OnMessageClickListener
import com.blueberrysolution.pinelib19.pop_up_windows.msgbox.WindowsStyle


class ActivityMessageBox: PineActivity(), View.OnClickListener {

    private var listener: OnMessageClickListener? = null

    lateinit var style: WindowsStyle
    lateinit var title: String
    lateinit var btns: Array<out String>

    lateinit var mainMassage: TextView;
    lateinit var btn1: TextView;
    lateinit var btn2: TextView;
    lateinit var btn3: TextView;
    lateinit var btn4: TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMassageBox = this;

        style = intent.getSerializableExtra("style") as WindowsStyle
        title = intent.getStringExtra("title")
        btns = intent.getSerializableExtra("btns") as Array<String>

        doContentView()

        setupBtns();


    }

    fun getWindowsStyle(): WindowsStyle {
        return style
    }


    fun setWindowsStyle(style: WindowsStyle): ActivityMessageBox {
        this.style = style
        return this
    }


    override fun onClick(v: View?) {

        if (listener != null) {
            if (v!!.id == R.id.btn1) {
                listener!!.messageBoxChoose(1)
            } else if (v!!.id == R.id.btn2) {
                listener!!.messageBoxChoose(2)
            } else if (v!!.id == R.id.btn3) {
                listener!!.messageBoxChoose(3)
            } else if (v!!.id == R.id.btn4) {
                listener!!.messageBoxChoose(4)
            }
        }
        finish()
    }




    fun setupBtns() {
        mainMassage = findViewById(R.id.mainMassage)
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)

        mainMassage!!.text = title

        btn1!!.visibility = View.VISIBLE
        btn2!!.visibility = View.GONE
        btn3!!.visibility = View.GONE
        btn4!!.visibility = View.GONE

        btn1!!.text = btns[0]
        if (btns.size >= 2) {
            btn2!!.text = btns[1]
            btn2!!.visibility = View.VISIBLE
        }
        if (btns.size >= 3) {
            btn3!!.text = btns[2]
            btn3!!.visibility = View.VISIBLE
        }
        if (btns.size >= 4) {
            btn4!!.text = btns[3]
            btn4!!.visibility = View.VISIBLE
        }

        btn1!!.setOnClickListener(this)
        btn2!!.setOnClickListener(this)
        btn3!!.setOnClickListener(this)
        btn4!!.setOnClickListener(this)
    }

    fun doContentView(){
        if (style == WindowsStyle.BlackNoFrame){
            setContentView(R.layout.msgbox_def);
        }else if (style == WindowsStyle.WhiteBottom){
            setContentView(R.layout.msgbox_white_bottom);
        }else if (style == WindowsStyle.WhiteMiddle){
            setContentView(R.layout.msgbox_white_middle);
        }else if (style == WindowsStyle.WhiteBottomNoTitle){
            setContentView(R.layout.msgbox_white_bottom_no_title);
        }


    }

    fun getListener(): OnMessageClickListener? {
        return listener
    }


    fun setListener(listener: OnMessageClickListener): ActivityMessageBox {
        this.listener = listener
        return this
    }

    fun setListener(func: (Int) -> Unit): ActivityMessageBox {
        this.listener = MessageBoxListener(func)
        return this
    }

    companion object {
        var activityMassageBox: ActivityMessageBox? = null;


    }
}