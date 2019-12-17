package com.blueberrysolution.pinelib19.activity

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.blueberrysolution.pinelib19.activity.global_exception.ExceptionExt
import com.blueberrysolution.pinelib19.addone.language.LocaleUtils
import com.blueberrysolution.pinelib19.addone.language.MyContextWrapper
import com.blueberrysolution.pinelib19.debug.G
import com.blueberrysolution.pinelib19.debug.big_window.functions.EnvironmentSwitch
import com.blueberrysolution.pinelib19.hardware.screen.ScreenUtils
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import java.util.*

//class App : BaseApp() {
//    init {
//        isDebug = true;
//        realtimeLogShow = false;
//        releaseSignature = "";
//
//    }
//
//}


open class BaseApp: MultiDexApplication {

    open  var isDebug: Boolean = true;
    open var realtimeLogShow: Boolean = false;
    open var keepScreenOn: Boolean = true;
    open var enableLeakCanary: Boolean = false;
    open var releaseSignature: String = "";
    open var googleTransalteProjectId: String = "drivingtestandro-1570664192412"


    var leakCandyWatcher: RefWatcher? = null;

    constructor(){
        baseApp = this;
        A.app(this);

    }

    override fun attachBaseContext(base: Context?) {
        val newLocale: Locale = LocaleUtils.getUserLocale(base!!);
        val context = MyContextWrapper.wrap(base!!, newLocale)

        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()


        //初始化语言
        LocaleUtils.updateLocale(LocaleUtils.getUserLocale());

        ExceptionExt().reg();


        // Access property for Context
        ZSignalCheck();
        EnvironmentSwitch.startCheck();

        installLeakCanary();

        if (isDebug){
            onDebugInit();
        }else{
            onLiveInit();
        }

    }


    fun installLeakCanary(){
        if ((isDebug) && (enableLeakCanary)){
            leakCandyWatcher = LeakCanary.install(this);

        }

    }

    open fun onDebugInit(){

    }

    open fun onLiveInit(){

    }


    companion object {
        var baseApp: BaseApp? = null;

        fun i(): BaseApp{
            if (baseApp == null){
                baseApp = BaseApp();
                G.d("严重错误，Application 没有被初始化！");

            }

            return baseApp!!



        }



    }



}
