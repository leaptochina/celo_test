package com.pine.celo.detail_activity


import android.content.Intent
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
import com.pine.celo.large_photo.LargePhotoActivity
import com.pine.celo.net.beans.UsersBean
import kotlinx.android.synthetic.main.detail_activity.*
import kotlinx.android.synthetic.main.main_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class DetailActivity : PineActivity() {

    var id = 1;
    lateinit  var data: DbUserBean;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)

        id = intent.getIntExtra("id", 0);

        data = M(DbUserBean::class).find(id)!!;


        name.text = data.title + " " + data.name;
        email.text = data.email;
        phone.text = data.phone;

        A.n().setImage(my_icon, data.large_pic, 1);


        my_icon.setOnClickListener {
            var intent = Intent(A.a(), LargePhotoActivity::class.java );
            intent.putExtra("url", data.large_pic)
            A.a().startActivity(intent);


        }
    }



}
