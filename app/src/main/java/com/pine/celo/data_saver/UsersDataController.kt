package com.pine.celo.data_saver

import com.blueberrysolution.pinelib19.addone.broadcast.Broadcast
import com.blueberrysolution.pinelib19.addone.share_preferences.Sp
import com.blueberrysolution.pinelib19.pop_up_windows.msgbox.MessageBox
import com.blueberrysolution.pinelib19.sqlite.mylib.model.M
import com.pine.celo.App
import com.pine.celo.net.beans.UsersBean
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersDataController{

    var users: ArrayList<DbUserBean> = ArrayList();


    constructor(){
        if (users.size == 0){
            users = M(DbUserBean::class).select();
        }
        if (users.size == 0){
            refreshData();
        }
    }

    fun loadMore(){
        var nextPage = Sp.i.get("nextPage", 1)


        var call = App.app?.retrofitManager?.req()?.users(nextPage, 10, Sp.i.get("seed", 10086L).toString())
        call!!.enqueue(object: Callback<UsersBean> {
            override fun onResponse(call: Call<UsersBean>, response: Response<UsersBean>) {
                Sp.i.put("nextPage", nextPage + 1)

                setupBody(response.body()!!)

                Broadcast.i.send("OnUsersRefreshed");

            }

            override fun onFailure(call: Call<UsersBean>, t: Throwable) {
                MessageBox.i().show("Cannot Load Data, Please Check the Internet Connection....")
            }


        })
    }

    fun refreshData(){
        Sp.i.put("seed", System.currentTimeMillis())

        var nextPage = 1;
        Sp.i.put("nextPage", nextPage)


        var call = App.app?.retrofitManager?.req()?.users(nextPage, 10, Sp.i.get("seed", 10086L).toString())
        call!!.enqueue(object: Callback<UsersBean> {
            override fun onResponse(call: Call<UsersBean>, response: Response<UsersBean>) {
                Sp.i.put("nextPage", nextPage + 1)

                users = ArrayList();

                M(DbUserBean::class).truncate();

                setupBody(response.body()!!)

                Broadcast.i.send("OnUsersRefreshed");

            }

            override fun onFailure(call: Call<UsersBean>, t: Throwable) {
                MessageBox.i().show("Cannot Load Data, Please Check the Internet Connection....")
            }


        })



    }

    private fun setupBody(body: UsersBean) {
        body.results.forEach {
            var dbUser = DbUserBean();
            dbUser.title = it.name.title;
            dbUser.name = it.name.first + " " + it.name.last;
            dbUser.gender = if (it.gender == "male") {"M"} else {"F"};
            dbUser.location = it.location.street.number + " " + it.location.street.name + " "+ it.location.city + " "+ it.location.state + " "+ it.location.country + " " + it.location.postcode;
            dbUser.email = it.email;
            dbUser.dob = it.dob.date;
            dbUser.phone = it.phone;
            dbUser.cell = it.cell;
            dbUser.thumb_pic = it.picture.thumbnail;
            dbUser.large_pic = it.picture.large;

            dbUser.save();
            // M(DbUserBean::class).save(dbUser);
            users.add(dbUser);

        }
    }


    companion object{
        var usersDataController: UsersDataController? = null;





        fun i(): UsersDataController{
            if (usersDataController == null){
                usersDataController = UsersDataController();
            }
            return usersDataController!!;
        }
    }
}