package com.blueberrysolution.pinelib19.sqlite.mylib.user_extend_models

import com.blueberrysolution.pinelib19.debug.G
import com.blueberrysolution.pinelib19.reflection.ReflectHelper
import com.blueberrysolution.pinelib19.sqlite.mylib.model.M
import kotlin.reflect.KClass

open class Relation: Crud() {

    fun <T : BModel> belongsTo(kclass: KClass<T>, relation_name: String? = null): M<T> {
        var relation_name_checked = relation_name;

        if (relation_name_checked == null){
            relation_name_checked = table_name + "_id";
        }
        val value = oldValues.getValue(relation_name_checked);
        if (value == null){
            G.e("SQL Select field ($relation_name_checked) nox exite in table ($table_name)")
        }
        return M(kclass).where("id", value!!)
    }

    fun <T : BModel> hasMany(kclass: KClass<T>, relation_name: String? = null): M<T> {
        var relation_name_checked = relation_name;

        if (relation_name_checked == null){
            relation_name_checked = table_name + "_id";
        }
        return M(kclass).where(relation_name_checked, id!!)
    }

    fun <T : BModel> hasOne(kclass: KClass<T>, relation_name: String? = null): T? {
        var relation_name_checked = relation_name;

        if (relation_name_checked == null){
            relation_name_checked = table_name + "_id";
        }
        return M(kclass).where(relation_name_checked, id!!).find();
    }

}