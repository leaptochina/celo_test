package com.pine.celo.main_activity

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blueberrysolution.pinelib19.activity.A
import com.blueberrysolution.pinelib19.activity.I
import com.blueberrysolution.pinelib19.addone.inject_replace.MyOnClickListener
import com.blueberrysolution.pinelib19.view.recycler_view.MyRecyViewHolder
import com.pine.celo.R
import com.pine.celo.data_saver.UsersDataController
import com.pine.celo.detail_activity.DetailActivity


class UsersAdapter(): RecyclerView.Adapter<MainActivityViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityViewHolder {
        //LayoutInflater.from指定写法
        return MyRecyViewHolder.i<MainActivityViewHolder>(parent, R.layout.main_activity_item)


    }




    override fun onBindViewHolder(holder: MainActivityViewHolder, position: Int) {
        var user = UsersDataController.i().users[position];

        holder.dob!!.text = user.dob;
        holder.user_name!!.text = user.title + " " + user.name;
        holder.gender!!.text = user.gender;

        A.n().setImage( holder.user_icon!!, user.thumb_pic, 1);

        holder.itemView.setOnClickListener {
            var intent = Intent(A.a(), DetailActivity::class.java );
            intent.putExtra("id", user.id)
            A.a().startActivity(intent);
        }

    }




    override fun getItemCount(): Int {
        return UsersDataController.i().users.size;
    }

}