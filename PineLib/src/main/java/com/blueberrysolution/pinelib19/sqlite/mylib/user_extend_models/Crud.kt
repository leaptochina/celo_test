package com.blueberrysolution.pinelib19.sqlite.mylib.user_extend_models

import com.blueberrysolution.pinelib19.reflection.ReflectHelper
import com.blueberrysolution.pinelib19.sqlite.mylib.model.M
import kotlin.math.abs

open class Crud: UserBaseModel() {
    fun save(): BModel {

        return M(superKClass).save(this as BModel);


    }


}