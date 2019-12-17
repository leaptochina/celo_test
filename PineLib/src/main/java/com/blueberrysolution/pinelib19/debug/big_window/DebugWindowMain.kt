package com.blueberrysolution.pinelib19.debug.big_window

import android.graphics.Color
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.blueberrysolution.pinelib19.R
import com.blueberrysolution.pinelib19.debug.DebugWindows
import com.blueberrysolution.pinelib19.debug.DebugWindows.hide
import com.blueberrysolution.pinelib19.debug.DebugWindows.show
import com.blueberrysolution.pinelib19.debug.big_window.function.FunctionListAdapter
import com.blueberrysolution.pinelib19.debug.big_window.functions.Functions
import com.blueberrysolution.pinelib19.view.recycler_view.RecyViewSetup
import androidx.recyclerview.widget.DividerItemDecoration
import com.blueberrysolution.pinelib19.activity.A
import com.blueberrysolution.pinelib19.addone.mytimer.MyTimer
import com.blueberrysolution.pinelib19.debug.big_window.logs.LogListAdapter
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class DebugWindowMain : DebugWindowBase() {

    var clostImage: FrameLayout? = null;
    var functionList: RecyclerView? = null;
    var logList: RecyclerView? = null;
    var errList: RecyclerView? = null;

    var btn1: FrameLayout? = null;
    var btn2: FrameLayout? = null;
    var btn3: FrameLayout? = null;

    var functionAdapter: FunctionListAdapter? = null;
    var logAdapter: LogListAdapter? = null;


    init{


    }

    fun showHideSwitch(){
        if (isShowing) hide();
        else showNow();

    }

    fun showNow(){
        if (isShowing) return;

        show();
        onInit()
    }

    fun hideNow(view: View){
        hide();
    }

    fun functionClick(view: View){
        functionList!!.visibility = View.VISIBLE;
        logList!!.visibility = View.GONE;
        errList!!.visibility = View.GONE;
    }

    fun logClick(view: View){
        functionList!!.visibility = View.GONE;
        logList!!.visibility = View.VISIBLE;
        errList!!.visibility = View.GONE;
    }

    fun errorClick(view: View){
        functionList!!.visibility = View.GONE;
        logList!!.visibility = View.GONE;
        errList!!.visibility = View.VISIBLE;
    }


    fun onInit() {
        clostImage = basicView!!.findViewById(R.id.close_img) as FrameLayout
        functionList = basicView!!.findViewById(R.id.debug_window_function_list) as RecyclerView
        logList = basicView!!.findViewById(R.id.debug_window_log_list) as RecyclerView
        errList = basicView!!.findViewById(R.id.debug_window_error_list) as RecyclerView

        //设置分割线
        val divider = DividerItemDecoration(A.c(), DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(A.c(),R.drawable.debug_window_divider)!!);
        functionList!!.addItemDecoration(divider);
        //logList!!.addItemDecoration(divider);
        errList!!.addItemDecoration(divider);


        btn1 = basicView!!.findViewById(R.id.btn1) as FrameLayout
        btn2 = basicView!!.findViewById(R.id.btn2) as FrameLayout
        btn3 = basicView!!.findViewById(R.id.btn3) as FrameLayout

        clostImage!!.setOnClickListener(::hideNow);
        btn1!!.setOnClickListener(::functionClick);
        btn2!!.setOnClickListener(::logClick);
        btn3!!.setOnClickListener(::errorClick);

        Functions.initAll();

        functionAdapter = FunctionListAdapter();
        RecyViewSetup(functionList!!, functionAdapter!!).build()

        logAdapter = LogListAdapter();
        RecyViewSetup(logList!!, logAdapter!!).build()
        MyTimer.i().setInterval(300).setOnTimerListener{
            doAsync {
                uiThread {
                    logAdapter!!.notifyDataSetChanged();
                }
            }

        }.start();

    }



}