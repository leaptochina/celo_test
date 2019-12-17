package com.blueberrysolution.pinelib19.permission


import android.annotation.TargetApi
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.provider.Settings
import com.blueberrysolution.pinelib19.R
import com.blueberrysolution.pinelib19.activity.A
import com.blueberrysolution.pinelib19.activity.t.T
import com.blueberrysolution.pinelib19.addone.mytimer.MyTimer
import com.blueberrysolution.pinelib19.addone.mytimer.OnTimerListener
import com.blueberrysolution.pinelib19.addone.diff_brand.miui.MIUIUtils


object RequireFloatWindow : OnTimerListener {
    override fun onTimer() {

        T.t(A.s(R.string.float_windows_allow));

        var intent:Intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        A.a().startActivityForResult(intent, 1);
    }

    fun isAllow(): Boolean{

        if (MIUIUtils.isMIUI()){
            return isMiUIFloatWindowsPermissionOn();
        }

        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(A.a())) {
                return false;
            }
        }


        return true;
    }

    /**
     * 4.4 以上可以直接判断准确
     *
     * 4.4 以下非MIUI直接返回true
     *
     * 4.4 以下MIUI 可 判断 上一次打开app 时 是否开启了悬浮窗权限
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun isMiUIFloatWindowsPermissionOn(): Boolean {
        val context: Context = A.c();
        val version = Build.VERSION.SDK_INT


        return if (version >= 19) {
            checkOp(
                context,
                24
            )  //自己写就是24 为什么是24?看AppOpsManager //AppOpsManager.OP_SYSTEM_ALERT_WINDOW
        } else {
            if (context.getApplicationInfo().flags and (1 shl 27) === 1 shl 27) {
                true
            } else {
                false
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun checkOp(context: Context, op: Int): Boolean {
        val version = Build.VERSION.SDK_INT

        if (version >= 19) {
            val manager = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            try {
                val managerClass = manager.javaClass
                val method = managerClass.getDeclaredMethod(
                    "checkOp",
                    Int::class.javaPrimitiveType,
                    Int::class.javaPrimitiveType,
                    String::class.java
                )
                val isAllowNum =
                    method.invoke(manager, op, Binder.getCallingUid(), context.packageName) as Int

                return if (AppOpsManager.MODE_ALLOWED === isAllowNum) {
                    true
                } else {
                    false
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return false
    }


    fun require(): Boolean{
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(A.a())) {

                T.t(A.s(R.string.float_windows_allow));

                MyTimer.i().setInterval(4000).setOnTimerListener(this).runInThread(false).startOnce();



                return false;
            } else {
                return true;
            }
        }

        return true;

    }
}