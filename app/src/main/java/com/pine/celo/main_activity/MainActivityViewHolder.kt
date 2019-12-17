package com.pine.celo.main_activity

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.blueberrysolution.pinelib19.view.recycler_view.MyRecyViewHolder


class MainActivityViewHolder(v: View): MyRecyViewHolder(v) {

    var gender: TextView? = null;
    var user_icon: ImageView? = null;
    var user_name: TextView? = null;
    var dob: TextView? = null;

}