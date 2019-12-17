package com.blueberrysolution.pinelib19.debug.big_window


import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.util.TypedValue
import android.widget.ListView
import android.widget.RelativeLayout
import com.blueberrysolution.pinelib19.R
import com.blueberrysolution.pinelib19.activity.A
import android.view.*
import android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
import android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf.flags
import android.util.TypedValue.COMPLEX_UNIT_DIP
import android.util.TypedValue.applyDimension
import android.widget.LinearLayout


open class DebugWindowBase{



    var isShowing = false;
    var basicView: View? = null;
    var mWindowManager: WindowManager? = null;
    val params = WindowManager.LayoutParams()
    var debugWindowInner: LinearLayout? = null;


    init{


    }






    fun hide(){
        if (isShowing == true){
            mWindowManager!!.removeView(basicView);
            isShowing = false;
        }

    }




    fun show(){
        if (isShowing) return;

        // 获取WindowManager
        mWindowManager = A.c().getSystemService(Context.WINDOW_SERVICE) as WindowManager


        //Setup View
        basicView = A.v(R.layout.debug_window_main_inner)
        debugWindowInner = basicView!!.findViewById<LinearLayout>(R.id.debug_window_inner)



        // 类型
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){//6.0
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else {
            params.type =  WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }

        // 设置flag


        val resources = A.c().getResources()
        val dm = resources.getDisplayMetrics()

        val linearParams = debugWindowInner!!.getLayoutParams()
        linearParams.height = dm.heightPixels * 7 / 10
        linearParams.width = dm.widthPixels * 9 / 10
        debugWindowInner!!.setLayoutParams(linearParams)

//        params.width = dm.widthPixels
//        params.height = dm.heightPixels

        params.flags =
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE

        // 不设置这个弹出框的透明遮罩显示为黑色
        params.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // 设置 FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按
        // 不设置这个flag的话，home页的划屏会有问题
        params.width = WindowManager.LayoutParams.WRAP_CONTENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        // params.gravity = Gravity.CENTER

        mWindowManager!!.addView(basicView, params)

        isShowing = true;
    }




}