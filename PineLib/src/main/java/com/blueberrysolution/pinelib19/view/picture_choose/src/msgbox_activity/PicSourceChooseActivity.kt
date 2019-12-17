package com.blueberrysolution.pinelib19.view.picture_choose.src.msgbox_activity

import android.Manifest
import android.os.Bundle
import android.view.View
import com.blueberrysolution.pinelib19.R
import com.blueberrysolution.pinelib19.activity.PineActivity
import com.blueberrysolution.pinelib19.view.picture_choose.P
import kotlinx.android.synthetic.main.picture_choose.*
import android.provider.MediaStore
import android.content.Intent
import androidx.core.content.FileProvider
import android.os.Environment.getExternalStorageDirectory
import com.blueberrysolution.pinelib19.activity.A
import com.blueberrysolution.pinelib19.permission.RequirePermission
import java.io.File
import android.app.Activity
import android.content.ContentUris
import android.provider.DocumentsContract
import android.annotation.TargetApi
import android.os.Build
import android.net.Uri
import com.blueberrysolution.pinelib19.activity.t.T


class PicSourceChooseActivity: PineActivity(){
    val REQUEST_CAMERA = 7834;
    val REQUEST_ALBUM = 7844;

    var cameraFileDir:String = "";
    var cameraFile:File? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.picture_choose)

        outter_layout.setOnClickListener(::onCancel);
        cancel.setOnClickListener(::onCancel);
        take_photo.setOnClickListener(::onTakePhoto);
        from_album.setOnClickListener(::onAlbumChoose);
    }

    fun onTakePhoto(view: View?){
        RequirePermission()
            .require(Manifest.permission.CAMERA)
            .require(Manifest.permission.READ_EXTERNAL_STORAGE)
            .require(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .requestPermission({
                useCamera()
            },{
                this.finish()
            })
    }

    fun onAlbumChoose(view: View){
        RequirePermission()
            .require(Manifest.permission.READ_EXTERNAL_STORAGE)
            .require(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .requestPermission({
                useAlbum()
            },{
                this.finish()
            })

    }

    private fun useAlbum(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra("crop", true)
        intent.putExtra("return-data", true)
        startActivityForResult(intent, REQUEST_ALBUM)
    }

    private fun useCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraFileDir = getExternalStorageDirectory().getAbsolutePath() + "/test/" + System.currentTimeMillis() + ".jpg"
        cameraFile = File(cameraFileDir);
        cameraFile!!.getParentFile().mkdirs()

         //改变Uri  com.xykj.customview.fileprovider注意和xml中的一致
        val uri = FileProvider.getUriForFile(this, "com.blueberrysolution.pinelib19.fileprovider", cameraFile!!)
         //添加权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(intent, REQUEST_CAMERA)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode === REQUEST_CAMERA){
            if (resultCode === Activity.RESULT_OK) {
                /*缩略图信息是储存在返回的intent中的Bundle中的，
                * 对应Bundle中的键为data，因此从Intent中取出
                * Bundle再根据data取出来Bitmap即可*/
//                val extras = data!!.extras
//                val bitmap = extras.get("data") as Bitmap

                P.getLastInstance()!!.onSinglePictureCallback(cameraFileDir!!);
                this.finish();
            }
            else{
                onCancel(null);
            }
        }
        else if (requestCode === REQUEST_ALBUM){
            if (resultCode === Activity.RESULT_OK) {
                if (Build.VERSION.SDK_INT >= 19) {
                    handleImageOnKitkat(data!!);
                } else {
                    handleImageBeforeKitkat(data!!);
                }

            }
            else{
                onCancel(null);
            }
        }












    }


    @TargetApi(19)
    private fun handleImageOnKitkat(data: Intent) {
        var imagePath: String? = null
        val uri = data.data
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的uri，则通过document id处理
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri!!.authority) {
                val id = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                val selection = MediaStore.Images.Media._ID + "=" + id
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection)
            } else if ("com.android.providers.downloads.documents" == uri.authority) {
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content:" + "//downloads/public_downloads"),
                    java.lang.Long.valueOf(docId)
                )
                imagePath = getImagePath(contentUri, null)
            }
        } else if ("content".equals(uri!!.scheme, ignoreCase = true)) {
            //如果是content类型的uri，则使用普通方式处理
            imagePath = getImagePath(uri, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            //如果是File类型的uri，直接获取图片路径即可
            imagePath = uri.path
        }
        displayImage(imagePath)//根据图片路径显示图片

    }

    private fun handleImageBeforeKitkat(data: Intent) {
        val uri = data.data
        val imagePath = getImagePath(uri!!, null)
        displayImage(imagePath)

    }

    private fun getImagePath(uri: Uri, selection: String?): String? {
        var path: String? = null
        //通过uri和selection来获取真实的图片路径
        val cursor = contentResolver.query(uri, null, selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path
    }

    private fun displayImage(imagePath: String?) {

        if (imagePath != null) {
            this.finish();
            P.getLastInstance()!!.onSinglePictureCallback(imagePath!!);

        } else {
            T.t(A.s(R.string.photo_choose_cannot_get_pic));
            onCancel(null);
        }
    }

    fun onCancel(view: View?){
        P.getLastInstance()!!.cancel();
        this.finish();
    }


}