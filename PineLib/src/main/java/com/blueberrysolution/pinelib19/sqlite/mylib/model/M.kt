package com.blueberrysolution.pinelib19.sqlite.mylib.model

import com.blueberrysolution.pinelib19.sqlite.mylib.user_extend_models.BModel
import kotlin.reflect.KClass

class M<T: BModel>: ModifyTableStructure<T>{


    constructor(kclass: KClass<T>): super(){
        this.kclass = kclass;

    }






}