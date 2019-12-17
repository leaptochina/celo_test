package com.pine.celo.net

import com.pine.celo.net.beans.UsersBean
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface RetrofitServices {


    @GET("api/")
    fun users(
        @Query("page") page: Int,
        @Query("results") results: Int = 5,
        @Query("seed") seed: String = ""
    ): Call<UsersBean>;

}




