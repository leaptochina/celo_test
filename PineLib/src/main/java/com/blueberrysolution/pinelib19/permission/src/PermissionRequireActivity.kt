package com.blueberrysolution.pinelib19.permission.src

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import com.blueberrysolution.pinelib19.R
import com.blueberrysolution.pinelib19.activity.A
import com.blueberrysolution.pinelib19.activity.PineActivity
import com.blueberrysolution.pinelib19.permission.RequirePermission
import com.blueberrysolution.pinelib19.pop_up_windows.msgbox.MessageBox
import kotlinx.android.synthetic.main.permission_require.*
import java.lang.StringBuilder

class PermissionRequireActivity: PineActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.permission_require)



        setupPermission();

        granted.setOnClickListener(::onGrantBtn);




    }

    private fun setupPermission() {

        var sb = StringBuilder();
        RequirePermission.lastInstance!!.permissionList.map {
            when (it) {
                Manifest.permission.CAMERA -> sb.append(A.s(R.string.permission_require_camara) + "\r\n")
                Manifest.permission.READ_CONTACTS -> sb.append(A.s(R.string.permission_require_READ_CONTACTS) + "\r\n")
                Manifest.permission.WRITE_CONTACTS -> sb.append(A.s(R.string.permission_require_WRITE_CONTACTS) + "\r\n")
                Manifest.permission.READ_EXTERNAL_STORAGE -> sb.append(A.s(R.string.permission_require_READ_EXTERNAL_STORAGE) + "\r\n")
                Manifest.permission.WRITE_EXTERNAL_STORAGE -> sb.append(A.s(R.string.permission_require_WRITE_EXTERNAL_STORAGE) + "\r\n")
                Manifest.permission.RECORD_AUDIO -> sb.append(A.s(R.string.permission_require_MICROPHONE) + "\r\n")
                Manifest.permission.READ_PHONE_STATE -> sb.append(A.s(R.string.permission_require_READ_PHONE_STATE) + "\r\n")
                Manifest.permission.ACCESS_COARSE_LOCATION -> sb.append(A.s(R.string.permission_require_ACCESS_COARSE_LOCATION) + "\r\n")
                Manifest.permission.ACCESS_FINE_LOCATION -> sb.append(A.s(R.string.permission_require_ACCESS_FINE_LOCATION) + "\r\n")

            }
            ""
        }

        permission_list.text = sb.toString();
    }


    fun onGrantBtn(view: View?){


        var hasCameraPermission = RequirePermission.lastInstance!!.hasPermissions();

        if (hasCameraPermission) {
            //有调起相机拍照。
            RequirePermission.onGranted!!()
        } else {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(
                A.a(), RequirePermission.lastInstance!!.permissionList,
                RequirePermission.PERMISSION_REQUEST_CODE
            )
        }

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == RequirePermission.PERMISSION_REQUEST_CODE){
            var isTotalGranted = true;
            grantResults.map {
                if (it == PackageManager.PERMISSION_DENIED){
                    isTotalGranted = false;
                }
            }
            if (isTotalGranted) {
                RequirePermission.onGranted!!()
                this.finish();
            } else {
                //拒绝权限，弹出提示框。
                MessageBox.i().setListener {
                    if (it == 1){
                        RequirePermission.lastInstance!!.onCancel()
                        this.finish();
                    }
                    else{
                        onGrantBtn(null)
                    }
                }.show(A.s(R.string.photo_choose_no_permission),A.s(R.string.key_cancel),A.s(R.string.key_retry) )

            }
        }



        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }



}