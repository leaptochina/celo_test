package com.blueberrysolution.pinelib19.addone.language

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.util.DisplayMetrics
import com.blueberrysolution.pinelib19.activity.A

import com.google.gson.Gson
import java.lang.Exception

import java.util.Locale

object LocaleUtils {
    /**
     * 中文
     */
    val LOCALE_CHINESE = Locale.CHINESE
    /**
     * 繁体中文
     */
    val LOCALE_TRADITIONAL_CHINESE = Locale.TRADITIONAL_CHINESE
    /**
     * 英文
     */
    val LOCALE_ENGLISH = Locale.ENGLISH

    /**
     * 保存SharedPreferences的文件名
     */
    private val LOCALE_FILE = "LOCALE_FILE"
    /**
     * 保存Locale的key
     */
    private val LOCALE_KEY = "LOCALE_KEY"

    /**
     * 获取用户设置的Locale
     * @param pContext Context
     * @return Locale
     */
    fun getUserLocale(pContext: Context = A.c()): Locale {
        try{
            val _SpLocale = pContext.getSharedPreferences(LOCALE_FILE, Context.MODE_PRIVATE)
            val _LocaleJson = _SpLocale.getString(LOCALE_KEY, "")
            return jsonToLocale(_LocaleJson)
        }
        catch (e: Exception){
            return getCurrentLocale();
        }

    }

    /**
     * 获取当前的Locale
     * @param pContext Context
     * @return Locale
     */
    fun getCurrentLocale(): Locale {
        try{
            val pContext: Context = A.app().applicationContext;
            val _Locale: Locale
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //7.0有多语言设置获取顶部的语言
                _Locale = pContext.resources.configuration.locales.get(0)
            } else {
                _Locale = pContext.resources.configuration.locale
            }
            return _Locale
        }
        catch (e: Exception){
            return Locale.getDefault();
        }

    }

    /**
     * 保存用户设置的Locale
     * @param pContext Context
     * @param pUserLocale Locale
     */
    fun saveUserLocale(pContext: Context, pUserLocale: Locale) {
        val _SpLocal = pContext.getSharedPreferences(LOCALE_FILE, Context.MODE_PRIVATE)
        val _Edit = _SpLocal.edit()
        val _LocaleJson = localeToJson(pUserLocale)
        _Edit.putString(LOCALE_KEY, _LocaleJson)
        _Edit.apply()
    }

    /**
     * Locale转成json
     * @param pUserLocale UserLocale
     * @return json String
     */
    private fun localeToJson(pUserLocale: Locale): String {
        val _Gson = Gson()
        return _Gson.toJson(pUserLocale)
    }

    /**
     * json转成Locale
     * @param pLocaleJson LocaleJson
     * @return Locale
     */
    private fun jsonToLocale(pLocaleJson: String?): Locale {
        val _Gson = Gson()
        return _Gson.fromJson(pLocaleJson, Locale::class.java)
    }

    /**
     * 更新Locale
     *  if (choosedId == 1){
    LocaleUtils.updateLocale(LocaleUtils.LOCALE_ENGLISH);
    }
    else if (choosedId == 2){
    LocaleUtils.updateLocale( LocaleUtils.LOCALE_CHINESE);
    }
    else if (choosedId == 3){
    LocaleUtils.updateLocale( LocaleUtils.LOCALE_TRADITIONAL_CHINESE);
    }

     * @param pNewUserLocale New User Locale
     */
    fun updateLocale( pNewUserLocale: Locale) {
        val pContext: Context = A.c();
        if (needUpdateLocale(pContext, pNewUserLocale)) {
            val _Configuration = pContext.resources.configuration


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                _Configuration.setLocale(pNewUserLocale)
                pContext.createConfigurationContext(_Configuration)
            }
            else {
                _Configuration.locale = pNewUserLocale
                val _DisplayMetrics = pContext.resources.displayMetrics
                pContext.resources.updateConfiguration(_Configuration, _DisplayMetrics)
            }






            saveUserLocale(pContext, pNewUserLocale)

            A.onUpdateLanguage();
        }
    }

    /**
     * 判断需不需要更新
     * @param pContext Context
     * @param pNewUserLocale New User Locale
     * @return true / false
     */
    fun needUpdateLocale(pContext: Context, pNewUserLocale: Locale?): Boolean {
        return pNewUserLocale != null && getCurrentLocale() != pNewUserLocale
    }








}