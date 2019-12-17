package com.blueberrysolution.pinelib19.view.transparent_notice

import android.os.Bundle
import com.blueberrysolution.pinelib19.R
import com.blueberrysolution.pinelib19.activity.PineActivity
import kotlinx.android.synthetic.main.activity_transparent_notice.*

//一个透明的提示窗体

class TransparentNoticeActivity : PineActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transparent_notice)

        var bg = intent.getIntExtra("background", R.drawable.green_circle_btn)
        outter_layout.setBackgroundResource(bg);


        outter_layout.setOnClickListener {
            this.finish()
        }
    }



}