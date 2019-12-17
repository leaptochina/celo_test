package com.pine.celo.main_activity

import android.os.Bundle
import android.view.View
import com.blueberrysolution.pinelib19.activity.PineActivity
import com.blueberrysolution.pinelib19.addone.broadcast.Broadcast
import com.blueberrysolution.pinelib19.addone.broadcast.OnBroadcast
import com.blueberrysolution.pinelib19.addone.datetime.DateTime
import com.blueberrysolution.pinelib19.addone.share_preferences.Sp
import com.blueberrysolution.pinelib19.view.recycler_view.RecyViewSetup
import com.blueberrysolution.pinelib19.view.recycler_view.RefreshLoadmoreListener
import com.pine.celo.App
import com.pine.celo.R
import com.pine.celo.data_saver.UsersDataController
import com.pine.celo.net.beans.UsersBean
import kotlinx.android.synthetic.main.main_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class MainActivity : PineActivity(), OnBroadcast {


    lateinit var adapter: UsersAdapter;
    lateinit var refreshLoadmoreListener: RefreshLoadmoreListener;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        adapter = UsersAdapter();
        refreshLoadmoreListener = RefreshLoadmoreListener(swipe_refreshlayout, ::onRefresh, ::onLoadmore)
        RecyViewSetup(buses_list, adapter).setOnRefreshLoadmoreListener(refreshLoadmoreListener).build()

        Broadcast.i.reg("OnUsersRefreshed", this)

    }

    private fun onLoadmore() {
        UsersDataController.i().loadMore();
    }

    override fun onBroadcast(key: String, withObject: Any?) {
        if (key == "OnUsersRefreshed"){
            adapter.notifyDataSetChanged();
            refreshLoadmoreListener.stopRefresh();
        }
    }

    private fun onRefresh() {

        UsersDataController.i().refreshData();
    }


}
