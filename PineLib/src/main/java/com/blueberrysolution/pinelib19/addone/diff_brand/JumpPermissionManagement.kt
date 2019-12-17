package com.blueberrysolution.pinelib19.addone.diff_brand

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.multidex.BuildConfig

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 */

object JumpPermissionManagement {
    /**
     * Build.MANUFACTURER
     */
    private val MANUFACTURER_HUAWEI = "Huawei"//华为
    private val MANUFACTURER_MEIZU = "Meizu"//魅族
    private val MANUFACTURER_XIAOMI = "Xiaomi"//小米
    private val MANUFACTURER_SONY = "Sony"//索尼
    private val MANUFACTURER_OPPO = "OPPO"
    private val MANUFACTURER_LG = "LG"
    private val MANUFACTURER_VIVO = "vivo"
    private val MANUFACTURER_SAMSUNG = "samsung"//三星
    private val MANUFACTURER_LETV = "Letv"//乐视
    private val MANUFACTURER_ZTE = "ZTE"//中兴
    private val MANUFACTURER_YULONG = "YuLong"//酷派
    private val MANUFACTURER_LENOVO = "LENOVO"//联想

    val miuiVersion: String?
        get() {
            val line: String
            var input: BufferedReader? = null
            try {
                val p = Runtime.getRuntime().exec("getprop ro.miui.ui.version.name")
                input = BufferedReader(InputStreamReader(p.inputStream), 1024)
                line = input.readLine()
                input.close()
            } catch (ex: IOException) {
                return null
            } finally {
                if (input != null) {
                    try {
                        input.close()
                    } catch (e: IOException) {
                    }

                }
            }
            return line
        }

    /**
     * 此函数可以自己定义
     *
     * @param activity
     */
    fun GoToSetting(activity: Activity) {
        when (Build.MANUFACTURER) {
            MANUFACTURER_HUAWEI -> Huawei(activity)
            MANUFACTURER_MEIZU -> Meizu(activity)
            MANUFACTURER_XIAOMI -> Xiaomi(activity)
            MANUFACTURER_SONY -> Sony(activity)
            MANUFACTURER_OPPO -> OPPO(activity)
            MANUFACTURER_LG -> LG(activity)
            MANUFACTURER_LETV -> Letv(activity)
            else -> {
                ApplicationInfo(activity)
                Log.e("goToSetting", "目前暂不支持此系统")
            }
        }
    }

    fun Huawei(activity: Activity) {
        val intent = Intent()
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID)
        val comp = ComponentName(
            "com.huawei.systemmanager",
            "com.huawei.permissionmanager.ui.MainActivity"
        )
        intent.component = comp
        activity.startActivity(intent)
    }

    fun Meizu(activity: Activity) {
        val intent = Intent("com.meizu.safe.security.SHOW_APPSEC")
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID)
        activity.startActivity(intent)
    }

    fun Xiaomi(activity: Activity) {
        // 只兼容miui v5/v6 的应用权限设置页面，否则的话跳转应用设置页面（权限设置上一级页面）
        val miuiVersion = miuiVersion
        var intent: Intent? = null
        if ("V5" == miuiVersion) {
            val packageURI = Uri.parse("package:" + activity.applicationInfo.packageName)
            intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI)
        } else if ("V6" == miuiVersion || "V7" == miuiVersion) {
            intent = Intent("miui.intent.action.APP_PERM_EDITOR")
            intent.setClassName(
                "com.miui.securitycenter",
                "com.miui.permcenter.permissions.AppPermissionsEditorActivity"
            )
            intent.putExtra("extra_pkgname", activity.packageName)
        } else if (("V8" == miuiVersion) || ("V9" == miuiVersion)) {
            intent = Intent("miui.intent.action.APP_PERM_EDITOR")
            intent.setClassName(
                "com.miui.securitycenter",
                "com.miui.permcenter.permissions.PermissionsEditorActivity"
            )
            intent.putExtra("extra_pkgname", activity.packageName)
        } else {
        }

        if (null != intent)
            activity.startActivity(intent)
    }

    fun Sony(activity: Activity) {
        val intent = Intent()
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID)
        val comp = ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity")
        intent.component = comp
        activity.startActivity(intent)
    }

    fun OPPO(activity: Activity) {
        val intent = Intent()
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID)
        val comp = ComponentName(
            "com.color.safecenter",
            "com.color.safecenter.permission.PermissionManagerActivity"
        )
        intent.component = comp
        activity.startActivity(intent)
    }

    fun LG(activity: Activity) {
        val intent = Intent("android.intent.action.MAIN")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID)
        val comp = ComponentName(
            "com.android.settings",
            "com.android.settings.Settings\$AccessLockSummaryActivity"
        )
        intent.component = comp
        activity.startActivity(intent)
    }

    fun Letv(activity: Activity) {
        val intent = Intent()
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID)
        val comp = ComponentName(
            "com.letv.android.letvsafe",
            "com.letv.android.letvsafe.PermissionAndApps"
        )
        intent.component = comp
        activity.startActivity(intent)
    }

    /**
     * 只能打开到自带安全软件
     *
     * @param activity
     */
    fun _360(activity: Activity) {
        val intent = Intent("android.intent.action.MAIN")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID)
        val comp = ComponentName(
            "com.qihoo360.mobilesafe",
            "com.qihoo360.mobilesafe.ui.index.AppEnterActivity"
        )
        intent.component = comp
        activity.startActivity(intent)
    }

    /**
     * 应用信息界面
     *
     * @param activity
     */
    fun ApplicationInfo(activity: Activity) {
        val localIntent = Intent()
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
            localIntent.data = Uri.fromParts("package", activity.packageName, null)
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.action = Intent.ACTION_VIEW
            localIntent.setClassName(
                "com.android.settings",
                "com.android.settings.InstalledAppDetails"
            )
            localIntent.putExtra("com.android.settings.ApplicationPkgName", activity.packageName)
        }
        activity.startActivity(localIntent)
    }

    /**
     * 系统设置界面
     *
     * @param activity
     */
    fun SystemConfig(activity: Activity) {
        val intent = Intent(Settings.ACTION_SETTINGS)
        activity.startActivity(intent)
    }
}