package com.pine.celo

import com.blueberrysolution.pinelib19.activity.A
import com.blueberrysolution.pinelib19.activity.BaseApp
import com.blueberrysolution.pinelib19.addone.share_preferences.Sp
import com.blueberrysolution.pinelib19.net.picasso.PicassoHelper
import com.blueberrysolution.pinelib19.net.retrofit.RetrofitManager
import com.blueberrysolution.pinelib19.sqlite.mylib.BbsSqlite
import com.blueberrysolution.pinelib19.sqlite.mylib.model.M
import com.pine.celo.data_saver.DbUserBean
import com.pine.celo.net.RetrofitServices


class App : BaseApp() {

    override var isDebug = false;
    override var releaseSignature = "0e07a3e5c0f2270447eb14c71665bf40";

    lateinit var sqlite: BbsSqlite;
    lateinit var retrofitManager: RetrofitManager<RetrofitServices>

    override fun onLiveInit(){
        super.onLiveInit()

        Config.baseUrl =  "https://randomuser.me";

        retrofitManager = RetrofitManager<RetrofitServices>(Config.baseUrl, RetrofitServices::class)
    }

    override fun onDebugInit() {
        super.onDebugInit()

        Config.baseUrl =  "https://randomuser.me";

        retrofitManager = RetrofitManager<RetrofitServices>(Config.baseUrl, RetrofitServices::class)
    }

    override fun onCreate() {
        super.onCreate()
        App.app = this;

        initDatabase();
        A.n(PicassoHelper())

    }




    fun initDatabase() {
        sqlite = BbsSqlite("mydb.db")

        if(!Sp.i.get("isDbInit", false)){


            M(DbUserBean::class).createTable()

            Sp.i.put("isDbInit", true);

        }



    }

    companion object{
        var app: App? = null;
    }

}