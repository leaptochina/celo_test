package com.blueberrysolution.pinelib19.debug.big_window.logs

import android.graphics.Color
import android.view.ViewGroup
import com.blueberrysolution.pinelib19.R
import com.blueberrysolution.pinelib19.activity.A
import com.blueberrysolution.pinelib19.debug.G
import com.blueberrysolution.pinelib19.debug.window.DebugWindowSmall
import com.blueberrysolution.pinelib19.debug.window.DebugWindowsController
import com.blueberrysolution.pinelib19.view.recycler_view.MyRecyViewHolder
import com.blueberrysolution.pinelib19.view.recycler_view.RecyclerViewBaseAdapter

class LogListAdapter: RecyclerViewBaseAdapter<LogAdapterViewHolder>() {

    var resource = A.c().resources;

    override fun getItemCount(): Int {
        return G.logList.count();
    }

    override fun onBindViewHolder(holder: LogAdapterViewHolder, position: Int) {
        var bean = G.logList[G.logList.count() - position - 1];

        holder.text_detail!!.setTextColor(resource.getColor(bean.color));
        holder.text_detail!!.text = bean.detail

        holder.debug_window_adapter_holder!!.setOnClickListener { view ->
            G.e(bean.detail);
        }




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogAdapterViewHolder {
        return MyRecyViewHolder.i<LogAdapterViewHolder>(parent, R.layout.debug_window_main_logs_adapter)
    }

}