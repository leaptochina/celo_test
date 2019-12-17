package com.blueberrysolution.pinelib19.sqlite.mylib.model

import android.database.Cursor
import com.blueberrysolution.pinelib19.activity.A
import com.blueberrysolution.pinelib19.addone.convert.AnyToString
import com.blueberrysolution.pinelib19.debug.G
import com.blueberrysolution.pinelib19.reflection.ReflectHelper
import com.blueberrysolution.pinelib19.sqlite.mylib.user_extend_models.BModel

open class SelectModel<T: BModel>: BaseModel<T>(){
    // SQL表达式

    private var selectSql = "SELECT%DISTINCT% %FIELD% FROM " +
            "%TABLE%%FORCE%%JOIN%%WHERE%%GROUP%%HAVING%%UNION%%ORDER%%LIMIT% %LOCK%%COMMENT%"



    protected var isSelectCount = false;
    protected var whereCondition: String? = "";
    protected var fields: Array<String> = arrayOf();
    protected var distinct: Boolean = false;
    protected var groupBy: String? = null;
    protected var having: String?= null;
    protected var orderBy: String? = null;
    protected var limit: Int = -1;

    protected var selectResult = ArrayList<T>();

    fun where(key: String, flag: String, value1: Any, value2: Any? = null): M<T> {
        var condition = "";

        parseWhereItem(key, flag, value1)


        return this as M<T>;
    }

    fun select(): ArrayList<T> {

        setupSelectTemplate();
        return doSelect(selectSql);
    }

    fun setupSelectTemplate(){
        selectSql = selectSqlTemplate;
        selectSql = selectSql.replace("%TABLE%", parseTable());
        selectSql = selectSql.replace("%DISTINCT%", parseDistinct());
        selectSql = selectSql.replace("%FIELD%", parseField());
        selectSql = selectSql.replace("%JOIN%", parseJoin());
        selectSql = selectSql.replace("%WHERE%", parseWhere());
        selectSql = selectSql.replace("%GROUP%", parseGroup());
        selectSql = selectSql.replace("%HAVING%", parseHaving());
        selectSql = selectSql.replace("%ORDER%", parseOrder());
        selectSql = selectSql.replace("%LIMIT%", parseLimit());
        selectSql = selectSql.replace("%UNION%", parseUnion());
        selectSql = selectSql.replace("%LOCK%", parseLock());
        selectSql = selectSql.replace("%COMMENT%", parseComment());
        selectSql = selectSql.replace("%FORCE%", parseForce());
    }

    fun count(): Int{
        isSelectCount = true;

        var result = select();


        return (result[0] as BModel).result_count;
    }

    //获取目前表的所有列 列名 类型
    fun getTableColumns(): ArrayList<String>{
        setupSelectTemplate();

        var arrayList =  ArrayList<String>();

        var r = A.db().rawQuery(selectSql, arrayOf())
        r.columnNames.map {
            arrayList.add(it);
        }

        return arrayList;
    }

    fun doSelect(sql: String): ArrayList<T>{
        G.d(selectSql);
        var r = A.db().rawQuery(selectSql, arrayOf())

        selectResult = ArrayList<T>();


        while (r.moveToNext()) {

            var result = HashMap<String, String?>();
            var obj = createKClassInstance();

            for (x in 0..(r.columnCount - 1)) {
                var key = r.getColumnName(x);
                var value = "";
                var type = r.getType(x);
                var valueConverted: Any? = null;
                if (type == Cursor.FIELD_TYPE_NULL) {
                    valueConverted = null;
                } else if (type == Cursor.FIELD_TYPE_BLOB) {
                    valueConverted = r.getBlob(x);
                } else if (type == Cursor.FIELD_TYPE_FLOAT) {
                    valueConverted = r.getFloat(x);
                } else if (type == Cursor.FIELD_TYPE_INTEGER) {
                    valueConverted = r.getInt(x);
                } else if (type == Cursor.FIELD_TYPE_STRING) {
                    valueConverted = r.getString(x);
                }

                result.put(key, AnyToString.toString(valueConverted))

                ReflectHelper.setValue(kclass, obj as Any, key, valueConverted);
            }
            ReflectHelper.setValue(kclass, obj as Any, "oldValues", result);
            
            selectResult.add(obj);
        }


        return  selectResult;

    }




    fun find(): T?{
        limit = 1;


        var selectResult = select();

        if (selectResult.count() >= 1)
        {
            return selectResult[0];
        }else{
            return null;
        }

    }

    fun find(id: Int): T?{
        return where("id", id).find();
    }




    fun parseDistinct(): String
    {
        return if (distinct) { " DISTINCT " } else {""};
    }

    fun parseField(): String{
        var fieldsStr = "";


        if (fields.count() == 0)
            fieldsStr = "*";
        else
        {
            fields.map{ key ->
                if (!fieldsStr.equals("")){
                    fieldsStr = fieldsStr + ",";
                }
                fieldsStr = fieldsStr + key + " AS " + key;
            }
        }


        if (isSelectCount){
            fieldsStr = "COUNT(" + fieldsStr + ") AS result_count"
        }


        return fieldsStr;
    }

    fun parseJoin() : String{
        return "";
    }

    fun parseWhere(): String{
//
//        if (!soft_delete.equals("")) {
//            parseWhereItem(soft_delete, "is", null)
//        }

        return whereCondition!!;
    }

    fun parseWhereItem(field: String, rule: String, value: Any?){

        var ruleChecked = rule.toLowerCase();
        var valueChecked: String = anyToString(value)




        var r = "`" + field + "`" + ruleChecked + valueChecked;


        if (whereCondition.equals(""))
        {
            whereCondition = " WHERE (" + r + ")"
        }
        else{
            whereCondition = whereCondition + " AND (" + r + ")"
        }

    }

    fun parseGroup(): String {
        return "";
    }

    fun parseHaving(): String {
        return "";
    }

    fun parseOrder(): String {
        return "";
    }

    fun parseLimit(): String {
        if (limit < 0){
            return ""
        }

        return " LIMIT " + limit.toString();
    }

    fun parseUnion(): String {
        return "";
    }

    fun parseLock(): String {
        return "";
    }

    fun parseComment(): String {
        return "";
    }

    fun parseForce(): String {
        return "";
    }




    fun where(key: String, value: Any): M<T> {
        return where(key, "=", value);
    }



    companion object {
        var selectSqlTemplate = "SELECT%DISTINCT% %FIELD% FROM " +
                "%TABLE%%FORCE%%JOIN%%WHERE%%GROUP%%HAVING%%UNION%%ORDER%%LIMIT% %LOCK%%COMMENT%"


    }

}