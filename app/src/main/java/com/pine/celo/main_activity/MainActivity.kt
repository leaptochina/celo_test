package com.pine.celo.main_activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CompoundButton
import android.widget.TextView
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

class MainActivity : PineActivity(), OnBroadcast, CompoundButton.OnCheckedChangeListener,
    TextWatcher {


    lateinit var adapter: UsersAdapter;
    lateinit var refreshLoadmoreListener: RefreshLoadmoreListener;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        adapter = UsersAdapter();
        refreshLoadmoreListener = RefreshLoadmoreListener(swipe_refreshlayout, ::onRefresh, ::onLoadmore)
        RecyViewSetup(buses_list, adapter).setOnRefreshLoadmoreListener(refreshLoadmoreListener).build()

        Broadcast.i.reg("OnUsersRefreshed", this)


        male.setOnCheckedChangeListener (this)
        female.setOnCheckedChangeListener(this)
        search.addTextChangedListener(this)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        UsersDataController.i().isFemale = female.isChecked;
        UsersDataController.i().isMale = male.isChecked;

        UsersDataController.i().filter();

    }

    private fun onLoadmore() {
        loading.visibility = View.VISIBLE
        UsersDataController.i().loadMore();
    }

    override fun onBroadcast(key: String, withObject: Any?) {
        if (key == "OnUsersRefreshed"){
            adapter.notifyDataSetChanged();
            refreshLoadmoreListener.stopRefresh();
            loading.visibility = View.INVISIBLE
        }
    }

    private fun onRefresh() {
        loading.visibility = View.VISIBLE
        UsersDataController.i().refreshData();
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {
        UsersDataController.i().textSearch = search.text.toString();

        UsersDataController.i().filter();
    }


}
