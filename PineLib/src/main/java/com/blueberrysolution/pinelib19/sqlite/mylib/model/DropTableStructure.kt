package com.blueberrysolution.pinelib19.sqlite.mylib.model

import com.blueberrysolution.pinelib19.activity.A
import com.blueberrysolution.pinelib19.reflection.ReflectHelper
import com.blueberrysolution.pinelib19.sqlite.mylib.user_extend_models.BModel
import kotlin.reflect.KClass
import kotlin.reflect.KClassifier
import kotlin.reflect.KType
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeCheckerImpl
import kotlin.reflect.jvm.javaType

open class DropTableStructure<T: BModel> : TruncateStructure<T>(){
    private var dropTableSql = "";

    fun dropTable(){


        dropTableSql = dropTableSqlTemplate;
        dropTableSql = dropTableSql.replace("%TABLE_NAME%", parseTable());


        A.db!!.execSQL(dropTableSql);
    }





    companion object {
        var dropTableSqlTemplate = "DROP TABLE if exists %TABLE_NAME%;"

    }
}