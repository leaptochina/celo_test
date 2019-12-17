package com.blueberrysolution.pinelib19.debug.big_window.function

import android.view.ViewGroup
import com.blueberrysolution.pinelib19.R
import com.blueberrysolution.pinelib19.debug.window.DebugWindowSmall
import com.blueberrysolution.pinelib19.debug.window.DebugWindowsController
import com.blueberrysolution.pinelib19.view.recycler_view.MyRecyViewHolder
import com.blueberrysolution.pinelib19.view.recycler_view.RecyclerViewBaseAdapter

class FunctionListAdapter: RecyclerViewBaseAdapter<FunctionAdapterViewHolder>() {

    override fun getItemCount(): Int {
        return DebugFunctions.beans.count();
    }

    override fun onBindViewHolder(holder: FunctionAdapterViewHolder, position: Int) {
        var bean = DebugFunctions.beans[position];
        holder.text_detail!!.text = bean.key;

        holder.debug_window_adapter_holder!!.setOnClickListener { view ->
            bean.func();

            DebugWindowsController.i().debugWindowSmall!!.debugWindowMain!!.hide();
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunctionAdapterViewHolder {
        return MyRecyViewHolder.i<FunctionAdapterViewHolder>(parent, R.layout.debug_window_main_function_adapter)
    }

}