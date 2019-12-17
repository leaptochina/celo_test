package com.blueberrysolution.pinelib19.sqlite.mylib.model

import com.blueberrysolution.pinelib19.activity.A
import com.blueberrysolution.pinelib19.reflection.ReflectHelper
import com.blueberrysolution.pinelib19.sqlite.mylib.user_extend_models.BModel
import kotlin.reflect.KClass
import kotlin.reflect.KClassifier
import kotlin.reflect.KType
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeCheckerImpl
import kotlin.reflect.jvm.javaType

open class TruncateStructure<T: BModel> : DeleteModel<T>(){
    private var truncateTableSql = "";

    fun truncate(){


        truncateTableSql = truncateTableSqlTemplate;
        truncateTableSql = truncateTableSql.replace("%TABLE_NAME%", parseTable());


        A.db!!.execSQL(truncateTableSql);
    }





    companion object {
        var truncateTableSqlTemplate = "DELETE FROM %TABLE_NAME%;"

    }
}