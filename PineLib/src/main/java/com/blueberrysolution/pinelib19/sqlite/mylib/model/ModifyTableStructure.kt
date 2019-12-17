package com.blueberrysolution.pinelib19.sqlite.mylib.model

import com.blueberrysolution.pinelib19.activity.A
import com.blueberrysolution.pinelib19.reflection.ReflectHelper
import com.blueberrysolution.pinelib19.sqlite.mylib.user_extend_models.BModel
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeCheckerImpl
import kotlin.reflect.jvm.javaType

open class ModifyTableStructure<T: BModel> : DeleteModel<T>(){
    private var insertColumnSql = "";


    fun createColumn(name: String, type: KClass<*>, default: String){
        insertColumnSql = insertColumnSqlTemplate;
        insertColumnSql = insertColumnSql.replace("%TABLE_NAME%", parseTable());

        var column = "'$name' ";
        when {
            type == Int::class -> column += "integer NOT NULL "
            type == String::class -> column += "varchar(255) "
            type == Boolean::class -> column += "BLOB "
            type == Float::class -> column += "real "
            type == Double::class -> column += "real "
        }

        if (default != ""){
            column += "DEFAULT $default "
        }

        insertColumnSql = insertColumnSql.replace("%COLUMNNAME%", column);
        A.db!!.execSQL(insertColumnSql);
    }

    private fun getModelColumns(): Map<String, KType> {

        var r = HashMap<String, KType>();

        var fields = ReflectHelper.getFields(kclass);
        fields.map {
            r.put(it.name, it.returnType);
        }


        return r
    }


    companion object {
        var insertColumnSqlTemplate = "ALTER TABLE %TABLE_NAME% ADD COLUMN %COLUMNNAME%;"


    }
}