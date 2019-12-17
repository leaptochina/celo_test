package com.blueberrysolution.pinelib19.net.okhttp

import android.os.Build
import android.util.Log
import com.blueberrysolution.pinelib19.debug.G
import com.blueberrysolution.pinelib19.net.entry.Funcs
import java.lang.Exception
import java.io.IOException
import android.widget.ImageView
import com.blueberrysolution.pinelib19.net.entry.Req
import com.blueberrysolution.pinelib19.activity.t.T
import android.os.Looper.getMainLooper
import okhttp3.*
import javax.xml.datatype.DatatypeConstants.SECONDS
import java.util.concurrent.TimeUnit


class OkHttp: Funcs {


    lateinit var client: OkHttpClient
    var header:HashMap<String, String> = HashMap();

    constructor(){
        //初始化OkHttpClient
        client = OkHttpClient().newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
            .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
            .writeTimeout(10, TimeUnit.SECONDS)//设置写入超时时间
            .build()

    }

    override fun addHeader(key: String, value: String): Funcs{
        header.put(key, value)
        return  this;
    }

    override fun setImage(imageView: ImageView, url: String, style: Int) {
        var req = Req();
        req.url = url;
        req.imageView = imageView;

        get(req);
    }

    //不知道为啥 每次都是GET 放弃了
    override fun post(
        url: String,
        data: HashMap<String, String>,
        callback: (body: String) -> Unit
    ) {
            G.d(url)
            try {

                val builder = FormBody.Builder()
                data.map{
                    builder.add(it.key, it.value);
                }

                val formBody = builder.build()

                val request = addHeaders().url(url).put(formBody).build()
                val call = client.newCall(request)
                call.enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        G.e(e.toString())
                    }

                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) {
                            val string = response.body!!.string()
                            callback(string)

                        } else {
                            G.e("服务器错误")
                        }
                    }
                })

            } catch (e: Exception) {
                G.e(e.toString())
            }






    }

    /**
     * 统一为请求添加头信息
     * @return
     */
    private fun addHeaders(): Request.Builder {
        var rb = Request.Builder();
        header.forEach{(key, value) -> rb = rb.addHeader(key, value) }

        return rb
            .addHeader("Connection", "keep-alive")
            .addHeader("platform", "2")
            .addHeader("phoneModel", Build.MODEL)
            .addHeader("systemVersion", Build.VERSION.RELEASE)

    }


    override fun get(req: Req) {
        try {
            G.d(req.url)
            var requestDetail = Request.Builder().url(req.url)
            header.forEach{(key, value) -> requestDetail = requestDetail.addHeader(key, value) }
            var request = requestDetail.build();

            var call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    req.onError(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        req.onResponse(response)

                    }
                    else{
                        var e = Exception("HTTP Load Error" + req.url);
                        req.onError(e)
                    }
                }
            })

        }
        catch (exception: Exception){
            req.onError(exception);
        }
    }

    override fun getSourceCode(url: String, callback: (body: String) -> Unit, errorCallback: (e: Exception) -> Unit) {
        var req = Req();
        req.url = url;
        req.onHtml = callback;
        req.onError = errorCallback;

        get(req);
    }
    override fun getSourceCode(url: String, callback: (body: String) -> Unit) {
        getSourceCode(url, callback, ::onException)
    }

    override fun getSourceCode(url: String): String {

        try {
            var request = Request.Builder().url(url).build();
            var response = client.newCall(request).execute()
            return response.body.toString();
        }
        catch (exception: Exception){
            onException(exception);
            return "";
        }


    }

    fun onException(e: Exception){
        G.w("Http Exception: " + e.toString());
    }
}