package com.blueberrysolution.pinelib19.debug.window

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import com.blueberrysolution.pinelib19.R
import com.blueberrysolution.pinelib19.activity.A
import com.blueberrysolution.pinelib19.activity.PineActivity
import com.blueberrysolution.pinelib19.addone.diff_brand.miui.MIUIAuthorityAdmin
import com.blueberrysolution.pinelib19.addone.diff_brand.miui.MIUIUtils
import com.blueberrysolution.pinelib19.addone.share_preferences.Sp
import com.blueberrysolution.pinelib19.permission.RequireFloatWindow
import kotlinx.android.synthetic.main.debug_window_permission_require.*

class DebugWindowRequirePermission : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.debug_window_permission_require)

        btn1.setOnClickListener(::onNoClick);
        btn2.setOnClickListener(::onNeverShowClick);
        btn3.setOnClickListener(::onYesClick);
    }

    override fun onResume() {
        super.onResume()

        if (RequireFloatWindow.isAllow()){
            DebugWindowsController.i().reInit();
            this.finish();
        }
    }

    fun onNoClick(v: View){
        this.finish();
    }

    fun onNeverShowClick(v: View){
        Sp.i.put("isLastTimeDisableDebugWindow", true);
        this.finish();
    }

    fun onYesClick(v: View){
        if (MIUIUtils.isMIUI()){
            MIUIAuthorityAdmin.gotoMiuiPermission();
        }
        else{
            var intent: Intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            A.a().startActivityForResult(intent, 1);
        }




    }
}
