package com.blueberrysolution.pinelib19.debug.big_window.functions

import android.app.ActivityManager
import android.content.Context
import com.blueberrysolution.pinelib19.R
import com.blueberrysolution.pinelib19.activity.A
import com.blueberrysolution.pinelib19.addone.language.LocaleUtils
import com.blueberrysolution.pinelib19.addone.restart.Restart
import com.blueberrysolution.pinelib19.debug.big_window.function.DebugFunctions
import com.blueberrysolution.pinelib19.pop_up_windows.msgbox.MessageBox

object LanguageChange{
    fun add(){
        DebugFunctions.add(A.s(R.string.float_windows_language), ::switchLanguage);
    }

    fun switchLanguage(){
        MessageBox.i().setListener{ choosedId ->
            if (choosedId == 1){
                LocaleUtils.updateLocale(LocaleUtils.LOCALE_ENGLISH);
            }
            else if (choosedId == 2){
                LocaleUtils.updateLocale( LocaleUtils.LOCALE_CHINESE);
            }
            else if (choosedId == 3){
                LocaleUtils.updateLocale( LocaleUtils.LOCALE_TRADITIONAL_CHINESE);
            }
            Restart.restartApp2();
        }
            .setCancelable(true)
            .show(A.s(R.string.float_windows_language_choose), "English", "简体中文", "繁體中文")
    }
}