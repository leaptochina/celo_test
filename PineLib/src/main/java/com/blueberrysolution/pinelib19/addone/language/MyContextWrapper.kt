package com.blueberrysolution.pinelib19.addone.language

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.Build.VERSION_CODES.KITKAT
import android.os.Build.VERSION.SDK_INT
import android.os.LocaleList
import com.blueberrysolution.pinelib19.net.entry.N
import java.util.*


class MyContextWrapper(base: Context) : android.content.ContextWrapper(base) {
    companion object {

        fun wrap(context: Context, newLocale: Locale): ContextWrapper {
            var context = context

            val res = context.getResources()
            val configuration = res.getConfiguration()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                configuration.setLocale(newLocale)
                val localeList = LocaleList(newLocale)
                LocaleList.setDefault(localeList)
                configuration.setLocales(localeList)
                context = context.createConfigurationContext(configuration)

            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                configuration.setLocale(newLocale)
                context = context.createConfigurationContext(configuration)

            }

            return ContextWrapper(context)
        }
    }
}