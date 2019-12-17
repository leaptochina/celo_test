package com.pine.celo.net.beans


import com.blueberrysolution.pinelib19.addone.serializable.SerializableClass

class UserBean : SerializableClass(){
    var gender: String = "";
    var name: UserNameBean = UserNameBean();
    var location:  LocationBean = LocationBean();
    var email: String = "";
    var phone: String = "";
    var cell: String = "";
    var dob: DobBean = DobBean();
    var picture: PictureBean = PictureBean();



}

class PictureBean : SerializableClass(){
    var large: String = "";
    var thumbnail: String = "";
}

class DobBean : SerializableClass(){
    var date: String = "";
    var age: Int = 0;
}

class UserNameBean : SerializableClass(){
    var title: String = "";
    var first: String = "";
    var last: String = "";
}


class LocationBean : SerializableClass(){
    var street: StreetBean =StreetBean();
    var city: String = "";
    var state: String = "";
    var country: String = "";
    var postcode: String = "";

}

class StreetBean : SerializableClass(){
    var number: String = "";
    var name: String = "";
}