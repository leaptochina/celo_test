package com.blueberrysolution.pinelib19.activity

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import com.blueberrysolution.pinelib19.addone.mytimer.MyTimer
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import com.blueberrysolution.pinelib19.addone.language.LocaleUtils
import com.blueberrysolution.pinelib19.net.entry.Funcs
import com.blueberrysolution.pinelib19.net.okhttp.OkHttp
import com.blueberrysolution.pinelib19.net.picasso.PicassoHelper
import java.util.*


class A(){

    companion object {

        var activity: Activity? = null;
        var app: BaseApp? = null;
        var db: SQLiteDatabase? = null;
        var conf: Configuration? = null;
        var resources: Resources? = null;
        var network: Funcs? = null;
        var okhttp: Funcs? = null;

        fun app(app: BaseApp? = null): BaseApp{
            if (app != null){
                this.app = app;
            }
            return this.app!!;
        }

        fun n(network: Funcs? = null): Funcs{
            if (network != null){
                this.network = network;
            }
            if (this.network == null){
                this.network = PicassoHelper();
            }
            return  this.network!!;
        }

        fun okhttp(): Funcs{
            if (this.okhttp == null){
                this.okhttp = OkHttp();
            }
            return  this.okhttp!!;
        }


        // 需要APP中初始化
        // sqlite = BbsSqlite("driving_test.db")
        fun db(db: SQLiteDatabase? = null): SQLiteDatabase{
            if (db != null){
                this.db = db;
            }
            return this.db!!;
        }

        fun a(activity: Activity? = null): Activity {
            if (activity != null){
                this.activity = activity;
            }
            return this.activity!!;
        }

        fun c(activity: Activity? = null): Context{
            if (activity != null){
                this.activity = activity;
            }

            if (this.activity != null){
                return this.activity!!.applicationContext;
            }
            else{
                return this.app!!.applicationContext;
            }

        }

        fun r(): Resources {
            return this.activity!!.resources;
        }


        //获取String信息
        fun ss(id: Int): String{
            var r: String?;
            r = activity?.getString(id);

            return if (r == null) {""} else {r};

        }

        fun s(id: Int, vararg  formatArgs: Any): String{
            if (conf == null){
                onUpdateLanguage();
            }

            var r: String?;
            r = resources!!.getString(id, *formatArgs);

            return r ?: "";

        }

        fun drawable(id: Int): Drawable{

            var r = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                resources!!.getDrawable(id, null)
            } else {
                resources!!.getDrawable(id)
            }


            return r;
        }


        fun color(id: Int): Int {

            var r= ContextCompat.getColor(A.c(), id)

            return r;

        }

        fun onUpdateLanguage(){
            conf = A.app!!.resources.getConfiguration()
            conf!!.locale = LocaleUtils.getUserLocale(); // locale I used here is Arabic
            resources = Resources(A.app!!.assets, A.app!!.resources.displayMetrics, conf); /* get localized string */
        }

        //装配View信息
        fun v(id: Int): View {
            val inflate = LayoutInflater.from(app!!.applicationContext)
            val view = inflate.inflate(id, null)
            return view;
        }


    }

}