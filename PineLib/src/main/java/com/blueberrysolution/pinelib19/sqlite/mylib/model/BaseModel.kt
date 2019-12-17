package com.blueberrysolution.pinelib19.sqlite.mylib.model

import com.blueberrysolution.pinelib19.activity.A
import com.blueberrysolution.pinelib19.addone.convert.Hump
import com.blueberrysolution.pinelib19.debug.window.DebugWindowsController
import com.blueberrysolution.pinelib19.reflection.ReflectHelper
import com.blueberrysolution.pinelib19.sqlite.mylib.user_extend_models.BModel
import kotlin.reflect.KClass


open class BaseModel<T: BModel> {


    lateinit var kclass: KClass<*>;


    fun createKClassInstance(): T {
        var instance = ReflectHelper.classToInstance(kclass) as T;
        ReflectHelper.setValue(kclass, instance, "superKClass", kclass);

        return instance
    }


    fun parseTable(): String{
        if ( classToTableName.containsKey(kclass)){
            return classToTableName.get(kclass)!!;
        }
        else{
            var table_name = Hump.humpToLine2(kclass.simpleName!!).substring(1);
            classToTableName.put(kclass, table_name);
            return table_name;
        }
    }

    fun getTableStructure(instance: T){
        var tableStructureSql = "select * from " + instance.table_name + " limit 0";
        var r = A.db().rawQuery(tableStructureSql, arrayOf())
        instance.oldValues.clear();
        r.columnNames.map {
            instance.oldValues.put(it, "")
        }
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








    companion object {
        var classToTableName =  HashMap<KClass<*>, String>();


    }

}