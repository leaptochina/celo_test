package com.pine.celo.data_saver

import com.blueberrysolution.pinelib19.sqlite.mylib.user_extend_models.BModel
import com.pine.celo.net.beans.DobBean
import com.pine.celo.net.beans.LocationBean
import com.pine.celo.net.beans.PictureBean
import com.pine.celo.net.beans.UserNameBean

class DbUserBean(): BModel() {

    var title: String = "";
    var name: String = "";
    var gender: String = "";
    var location: String = "";
    var email: String = "";
    var dob: String = "";
    var phone: String = "";
    var cell: String = "";
    var thumb_pic: String = "";
    var large_pic: String = "";

}