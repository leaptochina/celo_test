package com.blueberrysolution.pinelib19.view.picture_choose

import android.graphics.Bitmap
import com.blueberrysolution.pinelib19.activity.I
import com.blueberrysolution.pinelib19.pop_up_windows.activity_msgbox.BActivityMessageBox
import com.blueberrysolution.pinelib19.view.picture_choose.src.msgbox_activity.PicSourceChooseActivity
import com.blueberrysolution.pinelib19.view.picture_choose.src.transfer.PicTransformation
import java.io.File
import android.graphics.BitmapFactory
import com.blueberrysolution.pinelib19.pop_up_windows.waitting_new.Loading
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


//图片选择库
//功能不完善： 多选没做 剪裁没做
//用法
//P().setOnFile {
//    Loading.i().show(activity!!)
//    var imgFile = it[0]
//
//    var requestFile = imgFile.asRequestBody(("image/*").toMediaTypeOrNull())     // 需要手动导入 import okhttp3.RequestBody.Companion.asRequestBody
//    val body = MultipartBody.Part.createFormData("image", imgFile.name, requestFile)
//
//
//    var call = App.app?.retrofitManager?.req()?.uploadMemberIcon(body, Login.i().userBean!!.user_identity)
//    call!!.enqueue(object: Callback<UsersBean> {
//        override fun onResponse(call: Call<UsersBean>, response: Response<UsersBean>) {
//            if (response.code() == 200){
//                Login.reloadConfigs { configTopic, isSucc ->
//                    setupUI();
//                }
//            }
//            Loading.i().hide()
//        }
//
//        override fun onFailure(call: Call<UsersBean>, t: Throwable) {
//            Loading.i().hide()
//        }
//
//
//    })
//
//
//}
//.setAllowCrop(false)
//.setAllowMultipleChoose(false)
//.setAllowCamera(true)
//.setAllowGallery(true)
//.start()



class P{
    private var allowCamera = true;
    private var allowGallery = true;
    private var multipleChoose = false;
    private var allowCrop = false;

    private var onBitmap: ((bitmap: Array<Bitmap>) -> Unit)? = null
    private var onFile: ((url: Array<File>) -> Unit)? = null
    private var onFileUrl: ((url: Array<String>) -> Unit)? = null
    //private var onGalleryUrl: ((url: Array<String>) -> Unit)? = null
    private var onCancel: (() -> Unit)? = null

    var picTransformation: PicTransformation? = null;

    constructor(){
        P.p = this;
    }

    fun start(): P{
        picTransformation = PicTransformation();

        I.i(PicSourceChooseActivity::class).show()

        return this;
    }

    //当拍照结束回调，然后在判断是否需要剪裁
    fun onSinglePictureCallback(fileUrl0: String) {
        if (!allowCrop){
            callbackAll(arrayOf(fileUrl0))

        }else{

        }
    }

    //回调全部内容
    fun callbackAll(fileUrl0: Array<String>){
        fileUrl(fileUrl0)
        callbackBitmap(fileUrl0);
        callbackFile(fileUrl0)
    }

    fun callbackBitmap(fileUrl0: Array<String>) {
        if (onBitmap != null) {
            var bitmapArr: Array<Bitmap> = arrayOf();
            fileUrl0.map {
                val bitmap = BitmapFactory.decodeFile(it)
                bitmapArr = bitmapArr.plus(bitmap);
            }
            onBitmap!!(bitmapArr)
        }
    }

    fun callbackFile(fileUrl0: Array<String>) {
        if (onFile != null) {
            var fileArr: Array<File> = arrayOf();
            fileUrl0.map {
                val file = File(it)
                fileArr = fileArr.plus(file);
            }
            onFile!!(fileArr)
        }
    }


    // ============================================= 给内部类调用的 ===================================================

    fun cancel(){
        if (onCancel != null){
            onCancel!!();
        }
    }

    fun file(file: Array<File>){
        if (onFile != null){
            onFile!!(file);
        }
    }

    fun bitmap(bitmap: Array<Bitmap>){
        if (onBitmap != null){
            onBitmap!!(bitmap);
        }
    }

    fun fileUrl(file: Array<String>){
        if (onFileUrl != null){
            onFileUrl!!(file);
        }
    }

//    fun galleryUrl(file: Array<String>){
//        if (onGalleryUrl != null){
//            onGalleryUrl!!(file);
//        }
//    }









    // ============================================= get set ===================================================

    fun setAllowGallery(allow: Boolean): P{
        this.allowGallery = allow;
        return  this;
    }

    fun setAllowCamera(allow: Boolean): P{
        this.allowCamera = allow;
        return  this;
    }

    fun setAllowMultipleChoose(allow: Boolean): P{
        this.multipleChoose = allow;
        return  this;
    }

    fun setAllowCrop(allow: Boolean): P{
        this.allowCrop = allow;
        return  this;
    }

    fun setOnFile(listener: (bitmap: Array<File>) -> Unit): P{
        this.onFile = listener;
        return  this;
    }

    fun setOnBitmap(listener: (bitmap: Array<Bitmap>) -> Unit): P{
        this.onBitmap = listener;
        return  this;
    }

    fun setOnFileUrl(listener: (bitmap: Array<String>) -> Unit): P{
        this.onFileUrl = listener;
        return  this;
    }

//    fun setOnGalleryUrl(listener: (bitmap: Array<String>) -> Unit): P{
//        this.onGalleryUrl = listener;
//        return  this;
//    }

    fun setOnCancel(listener: () -> Unit): P{
        this.onCancel = listener;
        return  this;
    }



    companion object{
        var p: P? = null;
        fun getLastInstance(): P?{
            return p;
        }
    }
}
