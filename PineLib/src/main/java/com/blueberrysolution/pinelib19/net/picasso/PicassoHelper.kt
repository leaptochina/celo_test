package com.blueberrysolution.pinelib19.net.picasso

import android.widget.ImageView
import com.blueberrysolution.pinelib19.R
import com.blueberrysolution.pinelib19.net.entry.Funcs
import com.blueberrysolution.pinelib19.net.entry.Req
import com.squareup.picasso.Picasso
import com.squareup.picasso.PicassoProvider
import java.lang.Exception
import android.graphics.Bitmap
import com.squareup.picasso.OkHttp3Downloader
import android.os.Environment
import com.blueberrysolution.pinelib19.activity.A





class PicassoHelper: Funcs {

    override fun post(
        url: String,
        data: HashMap<String, String>,
        callback: (body: String) -> Unit
    ) {

    }

    override fun setImage(imageView: ImageView, url: String, style: Int){
        if (url == "") return;

        var p = Picasso.get().load(url).placeholder(R.drawable.jiazaizhong).error(R.drawable.jiazaishibai)
        if (style == 1){
            p = p.transform(CircleTransform())
        }
        p.into(imageView);
    }

    override fun addHeader(key: String, value: String): Funcs {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSourceCode(url: String, callback: (body: String) -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSourceCode(
        url: String,
        callback: (body: String) -> Unit,
        errorCallback: (e: Exception) -> Unit
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSourceCode(url: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun get(req: Req) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



}