package com.blueberrysolution.pinelib19.addone.diff_brand.miui

import android.os.Environment
import com.blueberrysolution.pinelib19.addone.share_preferences.Sp
import java.io.File
import java.io.FileInputStream
import java.lang.Exception
import java.util.*

object MIUIUtils {

    // 检测MIUI
    val KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    val KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    val KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    fun isMIUI(): Boolean {
        //获取缓存状态
        if(Sp.i.contain("isMIUI"))
        {
            return Sp.i.get("isMIUI", false);
        }
        val prop= Properties();
        var isMIUI: Boolean = false;
        try {
            prop.load(FileInputStream(File(Environment.getRootDirectory(), "build.prop")));
        } catch (e: Exception)
        {
            //e.printStackTrace();
            return false;
        }

        isMIUI = prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        Sp.i.put("isMIUI", isMIUI);

        return isMIUI;
    }


}