package com.blueberrysolution.pinelib19.addone.restart

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import com.blueberrysolution.pinelib19.activity.A
import kotlin.reflect.KClass


object Restart{

    fun exitApp() {
        //启动页
        android.os.Process.killProcess(android.os.Process.myPid())
    }

    /**
     * 重新启动App -> 杀进程,会短暂黑屏,启动慢
     */
    fun restartApp(className: KClass<*>) {
        //启动页
        val intent = Intent(A.a(), className.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        A.a().startActivity(intent)
        android.os.Process.killProcess(android.os.Process.myPid())
    }

    fun restartApp3() {
        //启动页
        val intent = A.c().getPackageManager()
            .getLaunchIntentForPackage(A.a().getPackageName())
        intent!!.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        A.a().startActivity(intent)
        android.os.Process.killProcess(android.os.Process.myPid())
    }

    /**
     * 重新启动App -> 不杀进程,缓存的东西不清除,启动快
     */
    fun restartApp2() {
        val intent = A.c().getPackageManager()
            .getLaunchIntentForPackage(A.a().getPackageName())
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        A.a().startActivity(intent)
    }
}