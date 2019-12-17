package com.blueberrysolution.pinelib19.sqlite.mylib.user_extend_models

import com.blueberrysolution.pinelib19.addone.convert.Hump
import kotlin.reflect.KClass

open class UserBaseModel {

    var id: Int? = -1;
    var superKClass: KClass<BModel> = BModel::class;
    var result_count = 0;
    var table_name = "";
    var soft_delete = "";
    var oldValues = HashMap<String, String?>();


    init{


        if (table_name.equals(""))
            table_name = Hump.humpToLine2(this.javaClass.simpleName).substring(1);

    }


    fun parseTable(): String
    {
        return "`" + table_name + "`" ;
    }

    fun anyToString(any: Any?): String{
        if (any == null)
            return "null"
        else if (any is String)
            return any
        else if (any is Int)
            return any.toString();
        else if (any is Double)
            return any.toString();
        else if (any is Float)
            return any.toString();
        else
            return "";
    }
}