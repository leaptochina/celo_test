package com.pine.celo.net

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface RetrofitServices {


    @GET("q/{question_list_id}/{is_correct}")
    fun reportMyAnswer(
        @Path("question_list_id") question_list_id: Int,
        @Path("is_correct") is_correct: Int
    ): Call<RequestBody>;

}




