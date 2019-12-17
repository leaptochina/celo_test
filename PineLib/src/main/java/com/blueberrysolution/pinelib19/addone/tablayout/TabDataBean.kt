package com.blueberrysolution.pinelib19.addone.tablayout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter

class TabDataBean{
    var view: androidx.fragment.app.Fragment;
    var title: String;
    var icon: Int = 0;
    var icon_choosed: Int = 0;

    constructor(view: androidx.fragment.app.Fragment, title: String,icon_choosed: Int = 0, icon: Int = 0){
        this.view = view;
        this.title = title;
        this.icon = icon;
        this.icon_choosed = icon_choosed;
    }
}