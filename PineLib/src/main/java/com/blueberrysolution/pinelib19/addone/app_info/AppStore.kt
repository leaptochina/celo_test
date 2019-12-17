package com.blueberrysolution.pinelib19.addone.app_info

import android.content.Intent
import android.net.Uri
import com.blueberrysolution.pinelib19.activity.A

object AppStore{
    fun jumpToAppStore(){
        val intent = Intent(Intent.ACTION_VIEW)
        val uri =
            Uri.parse("market://details?id=" + A.c().packageName)//app包名
        intent.data = uri
        intent.setPackage("com.android.vending")//Google应用市场包名
        A.a().startActivity(intent)
    }
}