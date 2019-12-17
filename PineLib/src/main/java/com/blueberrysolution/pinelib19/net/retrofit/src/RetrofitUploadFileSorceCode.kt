package com.blueberrysolution.pinelib19.net.retrofit.src

import com.blueberrysolution.pinelib19.activity.t.T
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import java.io.File

//@Multipart
//@POST("/u/icon/{user_identity}")
//fun uploadMemberIcon(@Part image: MultipartBody.Part //这里不能有括号
//                     ,@Path("user_identity") user_identity: String
//): Call<UsersBean>

//用法
class RetrofitUploadFileSorceCode {

    fun usage(imgFile: File){

//
//        var requestFile = imgFile.asRequestBody(("image/*").toMediaTypeOrNull())     // 需要手动导入 import okhttp3.RequestBody.Companion.asRequestBody
//        val body = MultipartBody.Part.createFormData("image", imgFile.name, requestFile)
//
//
//        var call = App.app?.retrofitManager?.req()?.uploadMemberIcon(body, Login.i().userBean!!.user_identity)
//
//        call!!.enqueue(object: Callback<UsersBean> {
//            override fun onResponse(call: Call<UsersBean>, response: Response<UsersBean>) {
//                if (response.code() == 200){
//                    var body = response.body()!!;
//
//
//
//
//                }
//            }
//
//            override fun onFailure(call: Call<UsersBean>, t: Throwable) {
//                T.t("err")
//            }
//
//
//        })
    }
}