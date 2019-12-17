package com.blueberrysolution.pinelib19.sqlite.mylib.model

import com.blueberrysolution.pinelib19.activity.A
import com.blueberrysolution.pinelib19.debug.G
import com.blueberrysolution.pinelib19.reflection.ReflectHelper
import com.blueberrysolution.pinelib19.sqlite.mylib.user_extend_models.BModel
import kotlin.math.abs

open class UpdateModel<T: BModel> : SelectModel<T>(){
    var updateSql = "";
    var setMap = HashMap<String, String>();

    open fun save(instance: T): T{
        setMap.clear();
        whereCondition = "";

        //检查哪些数据有变化
        instance.oldValues.forEach {
            var key = it.key;
            var oldValue = it.value;
            var newValue = ReflectHelper.getValue(kclass, instance, key);

            if (key == "id"){
                where("id", oldValue!!);
            }
            else {
                if (newValue != null) {
                    if (isChanged(newValue, oldValue)) {
                        setMap.put(key, newValue.toString())
                    }
                }
            }
        }
        if (setMap.count() > 0){
            setupUpdateTemplate();
            G.d(updateSql);
            A.db!!.execSQL(updateSql);
        }

        return instance;

    }
    fun parseSet(): String{
        var r = ""
        setMap.map {
            if (r == ""){
                r += "'" + it.key + "' = '" + it.value + "'"
            }
            else{
                r += ", '" + it.key + "' = '" + it.value + "'"
            }


        }

        return  r;
    }

    fun setupUpdateTemplate(){
        updateSql = updateSqlTemplate;
        updateSql = updateSql.replace("%TABLE%", parseTable());
        updateSql = updateSql.replace("%SET%", parseSet());
        updateSql = updateSql.replace("%JOIN%", parseJoin());
        updateSql = updateSql.replace("%WHERE%", parseWhere());
        updateSql = updateSql.replace("%LOCK%", parseLock());
        updateSql = updateSql.replace("%COMMENT%", parseComment());

    }


    fun isChanged(newValue: Any, oldValue: String?): Boolean{
        if (oldValue == null){
            return true
        }
        else if (newValue is Int){
            return (newValue.toString() != oldValue)
        }
        else if (newValue is String){
            return (newValue.toString() != oldValue)
        }
        else if (newValue is Boolean){
            return (newValue != (oldValue.toLowerCase() == "true"))
        }
        else if (newValue is Float){
            return (abs(newValue.toFloat() - oldValue.toFloat()) > 0.0000001)
        }
        else if (newValue is Double){
            return (abs(newValue.toDouble() - oldValue.toDouble()) > 0.0000001)
        }

        return false;
    }

    companion object {
        var updateSqlTemplate = "UPDATE %TABLE% SET %SET%%JOIN%%WHERE% %LOCK%%COMMENT%"


    }

}