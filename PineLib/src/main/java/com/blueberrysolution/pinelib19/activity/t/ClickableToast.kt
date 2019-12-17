package com.blueberrysolution.pinelib19.activity.t

import android.content.Context
import java.lang.reflect.AccessibleObject.setAccessible
import android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
import android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf.flags
import com.blueberrysolution.pinelib19.activity.t.ClickToast
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.icu.util.TimeUnit
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.blueberrysolution.pinelib19.R
import com.blueberrysolution.pinelib19.activity.A
import com.blueberrysolution.pinelib19.addone.mytimer.MyTimer
import com.blueberrysolution.pinelib19.addone.mytimer.OnTimerListener
import io.reactivex.Flowable
import org.intellij.lang.annotations.Flow
import java.util.*
import java.lang.reflect.AccessibleObject.setAccessible




abstract class ClickToast: OnTimerListener {

    private var mToast: Toast? = null
    private var isCancelled = false;
    private var myTimer: MyTimer? = null;
    private var duation: Int = -1;
    private var showingLeftMillsec: Int = -1;

    abstract fun setupView(view: View);
    abstract fun getLayout(): Int;

    fun setDuation(millsecound: Int): ClickToast{
        duation = millsecound;

        return  this;
    }

    fun show(): ClickToast {
        showingLeftMillsec = duation;

        showNow();

        myTimer = MyTimer.i().setInterval(3000).setCallbackInMainThread(true).setOnTimerListener(this).start();
        return  this;
    }

    fun showNow() {
        isCancelled = false;
        var context: Context = A.c();

        if (mToast != null){
            mToast!!.cancel();
        }
        if (duation != -1){
            showingLeftMillsec -= 3000;
            if (showingLeftMillsec <= 0){
                myTimer!!.stop();
            }
        }


        val inflater =
            context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        //自定义布局
        val view = inflater.inflate(getLayout(), null)
        setupView(view);

        mToast = Toast.makeText(context.getApplicationContext(), "", Toast.LENGTH_LONG)
        //这里可以指定显示位置
        //            mToast.setGravity(Gravity.BOTTOM, 0, 0);
        mToast!!.setGravity(Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL, 0, 0)
        mToast!!.setView(view)


        try {
            val mTN: Any?
            mTN = getField(mToast!!, "mTN")
            if (mTN != null) {
                val mParams = getField(mTN, "mParams")
                if (mParams != null && mParams is WindowManager.LayoutParams) {
                    val params = mParams as WindowManager.LayoutParams?
                    //显示与隐藏动画
                    params!!.windowAnimations = R.style.ClickToast
                    //Toast可点击
                    params!!.flags =
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE

                    //设置viewgroup宽高
                    params!!.width = WindowManager.LayoutParams.MATCH_PARENT //设置Toast宽度为屏幕宽度
                    params!!.height = WindowManager.LayoutParams.WRAP_CONTENT //设置高度
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


        mToast!!.show()



    }

    override fun onTimer() {

        if (isCancelled){
            myTimer!!.stop();
        }
        else{
            showNow();
        }
    }

    fun cancel(){
        isCancelled = true;
        if(mToast != null)
            mToast!!.cancel()

    }

    /**
     * 反射字段
     * @param object 要反射的对象
     * @param fieldName 要反射的字段名称
     */
    @Throws(NoSuchFieldException::class, IllegalAccessException::class)
    private fun getField(`object`: Any, fieldName: String): Any? {
        val field = `object`.javaClass.getDeclaredField(fieldName)
        if (field != null) {
            field.isAccessible = true
            return field.get(`object`)
        }
        return null
    }

}