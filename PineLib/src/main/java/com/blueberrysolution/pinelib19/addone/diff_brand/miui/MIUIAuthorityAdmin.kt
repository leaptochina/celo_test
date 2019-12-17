package com.blueberrysolution.pinelib19.addone.diff_brand.miui

import android.content.ComponentName
import android.content.Intent
import com.blueberrysolution.pinelib19.activity.A
import androidx.core.content.ContextCompat.startActivity
import com.blueberrysolution.pinelib19.addone.diff_brand.JumpPermissionManagement


object MIUIAuthorityAdmin{

    fun gotoMiuiPermission() {
        JumpPermissionManagement.GoToSetting(A.a())

    }
}