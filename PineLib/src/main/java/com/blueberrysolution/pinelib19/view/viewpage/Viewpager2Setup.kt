package com.blueberrysolution.pinelib19.view.viewpage


import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2


import com.blueberrysolution.pinelib19.activity.A

/**
 *         使用方法
adapter = StopDetailAdapter(this);
refreshLoadmoreListener = RefreshLoadmoreListener(swipe_refreshlayout, ::onRefresh)
RecyViewSetup(buses_list, adapter).setOnRefreshLoadmoreListener(refreshLoadmoreListener).build()


 */
class Viewpager2Setup {


    var recycleView: ViewPager2;
    var divider = DividerItemDecoration(A.a(), LinearLayoutManager.VERTICAL)
    var adapter: RecyclerView.Adapter<*>;


    constructor(recycleView: ViewPager2, adapter: RecyclerView.Adapter<*>){
        this.recycleView = recycleView;
        this.adapter = adapter;
    }



    fun build(): Viewpager2Setup {

        recycleView.adapter = adapter;
        recycleView.addItemDecoration(divider)



        return this;
    }


}