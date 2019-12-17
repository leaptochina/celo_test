package com.blueberrysolution.pinelib19.debug.window

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.widget.ListView
import android.widget.RelativeLayout
import com.blueberrysolution.pinelib19.R
import com.blueberrysolution.pinelib19.activity.A
import android.view.*
import android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
import android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
import com.blueberrysolution.pinelib19.addone.share_preferences.Sp
import com.blueberrysolution.pinelib19.debug.big_window.DebugWindowMain
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf.flags






class DebugWindowSmall{

    var isShowing = false;
    var basicView: View? = null;
    var debugWindowImage: RelativeLayout? = null;
    var mWindowManager: WindowManager? = null;
    val params = WindowManager.LayoutParams()
    var debugWindowMain: DebugWindowMain? = null;


    init{


    }

    fun hide(){
        mWindowManager!!.removeView(basicView);
        isShowing = false;
    }


    var lastX: Float = 0.0f
    var lastY:Float = 0.0f
    var paramX: Int =  0
    var paramY:Int = 0


    fun onTouch(v: View, event: MotionEvent): Boolean {

        when (event.getAction()) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.getRawX()
                lastY = event.getRawY()
                paramX = params.x
                paramY = params.y
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = event.getRawX() - lastX
                val dy = event.getRawY() - lastY
                params.x = (paramX + dx).toInt()
                params.y = (paramY + dy).toInt()
                // 更新悬浮窗位置
                mWindowManager!!.updateViewLayout(basicView, params)
            }
            MotionEvent.ACTION_UP -> {
                val dx = event.getRawX() - lastX
                val dy = event.getRawY() - lastY
                params.x = (paramX + dx).toInt()
                params.y = (paramY + dy).toInt()
                Sp.i.put("debug_windows_x", params.x);
                Sp.i.put("debug_windows_y", params.y);
            }
        }
        return false
    }


    fun onClick(arg0: View) {
        if (debugWindowMain == null){
            debugWindowMain = DebugWindowMain();
        }

        debugWindowMain!!.showHideSwitch();

    }


    fun show(){
        if (isShowing) hide();

        // 获取WindowManager
        mWindowManager = A.c().getSystemService(Context.WINDOW_SERVICE) as WindowManager


        //Setup View
        basicView = LayoutInflater.from(A.c().applicationContext).inflate(R.layout.debug_window_small,null)
        debugWindowImage = basicView!!.findViewById(R.id.debug_window_image) as RelativeLayout

        debugWindowImage!!.setOnTouchListener(::onTouch)
        debugWindowImage!!.setOnClickListener(::onClick)

        // 类型
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){//6.0
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else {
            params.type =  WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
           // params.type =  WindowManager.LayoutParams.TYPE_TOAST;
        }

        // 设置flag






        params.x = Sp.i.get("debug_windows_x", 60)
        params.y = Sp.i.get("debug_windows_y", 60)

        params.flags =
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE

        // 不设置这个弹出框的透明遮罩显示为黑色
        params.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // 设置 FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按
        // 不设置这个flag的话，home页的划屏会有问题
        params.width = WindowManager.LayoutParams.WRAP_CONTENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        params.gravity = Gravity.TOP or Gravity.LEFT or Gravity.START;


        mWindowManager!!.addView(basicView, params)

        isShowing = true;
    }




}