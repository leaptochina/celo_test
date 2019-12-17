package com.pine.celo.large_photo

import android.os.Bundle
import android.view.View
import com.blueberrysolution.pinelib19.activity.A
import com.blueberrysolution.pinelib19.activity.PineActivity
import com.blueberrysolution.pinelib19.addone.broadcast.Broadcast
import com.blueberrysolution.pinelib19.addone.broadcast.OnBroadcast
import com.blueberrysolution.pinelib19.addone.datetime.DateTime
import com.blueberrysolution.pinelib19.addone.share_preferences.Sp
import com.blueberrysolution.pinelib19.sqlite.mylib.model.M
import com.blueberrysolution.pinelib19.view.recycler_view.RecyViewSetup
import com.blueberrysolution.pinelib19.view.recycler_view.RefreshLoadmoreListener
import com.pine.celo.App
import com.pine.celo.R
import com.pine.celo.data_saver.DbUserBean
import com.pine.celo.data_saver.UsersDataController
import com.pine.celo.net.beans.UsersBean
import kotlinx.android.synthetic.main.detail_activity.*
import kotlinx.android.synthetic.main.large_photo_activity.*
import kotlinx.android.synthetic.main.main_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class LargePhotoActivity : PineActivity() {

    var url = "";


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.large_photo_activity)

        url = intent.getStringExtra("url");




        A.n().setImage(imageView, url, 0);

    }



}
