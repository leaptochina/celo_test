package com.blueberrysolution.pinelib19.sqlite.mylib.model

import com.blueberrysolution.pinelib19.activity.A
import com.blueberrysolution.pinelib19.reflection.ReflectHelper
import com.blueberrysolution.pinelib19.sqlite.mylib.user_extend_models.BModel
import kotlin.reflect.KClass
import kotlin.reflect.KClassifier
import kotlin.reflect.KType
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeCheckerImpl
import kotlin.reflect.jvm.javaType

open class CreateTableStructure<T: BModel> : ModifyTableStructure<T>(){
    private var createTableSql = "";

    fun createTable(){
        dropTable();


        createTableSql = createTableSqlTemplate;
        createTableSql = createTableSql.replace("%TABLE_NAME%", parseTable());

        var columns = "";
        var models = getModelColumns();
        models.forEach{
            if ((it.key != "id") && (it.key != "oldValues") && (it.key != "table_name")&& (it.key != "superKClass")&& (it.key != "result_count")){
                var c = getColumn(it.key, it.value.classifier, "")
                if (columns != ""){
                    columns += ","
                }
                columns += c;
            }


        }


        createTableSql = createTableSql.replace("%COLUMNNAME%", columns);
        A.db!!.execSQL(createTableSql);
    }

    fun getColumn(name: String, type: KClassifier?, default: String): String{

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

        return column;

    }



    companion object {
        var createTableSqlTemplate = "CREATE TABLE %TABLE_NAME%(id INT PRIMARY KEY NOT NULL, %COLUMNNAME%);"

    }
}