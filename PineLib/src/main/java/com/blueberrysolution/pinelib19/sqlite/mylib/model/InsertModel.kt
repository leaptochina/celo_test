package com.blueberrysolution.pinelib19.sqlite.mylib.model

import android.content.ContentValues
import com.blueberrysolution.pinelib19.activity.A
import com.blueberrysolution.pinelib19.debug.G
import com.blueberrysolution.pinelib19.reflection.ReflectHelper
import com.blueberrysolution.pinelib19.sqlite.mylib.user_extend_models.BModel
import com.blueberrysolution.pinelib19.activity.t.T



open class InsertModel<T: BModel>: UpdateModel<T>(){
    var insertSql = "";

    var insertFields = "";
    var insertData = "";
    var insertValues = ContentValues()

    fun newInstance(): T{
        var tIntance = ReflectHelper.classToInstance(kclass);
        ReflectHelper.setValue(kclass, tIntance, "id", -1);
        ReflectHelper.setValue(kclass, tIntance, "superKClass", kclass);


        return  tIntance as T
    }

    override fun save(instance: T): T{
        val oldId = ReflectHelper.getValue(kclass, instance, "oldValues") as  HashMap<String, String?>

        if (oldId.count() != 0) return super.save(instance);

        getTableStructure(instance);
        setupIdValue(instance);
        setupFieldData(instance);



        setupInsertTemplate();
        G.d(insertSql);
        A.db!!.insert(parseTable(), null, insertValues);

        var newInstance = M(instance.superKClass).find(instance.id!!)
        return newInstance as T;

    }

    fun setupIdValue(instance: T) {
        if (instance.id == -1){
            var select = "SELECT id from " + parseTable() + " order by id desc limit 1";
            var r = A.db().rawQuery(select , arrayOf());
            if (r.count == 0){
                instance.id = 1;
            }else{
                r.moveToFirst();
                instance.id = r.getInt(0) + 1
            }


        }
    }

    fun setupInsertTemplate(){
        insertSql = insertSqlTemplate;
        insertSql = insertSql.replace("%INSERT%", parseInsert());
        insertSql = insertSql.replace("%TABLE%", parseTable());
        insertSql = insertSql.replace("%FIELD%", parseInsertField());
        insertSql = insertSql.replace("%DATA%", parseData());
        insertSql = insertSql.replace("%COMMENT%", parseComment());
    }

    fun setupFieldData(instance: T){
        insertFields = "";
        insertData = "";
        insertValues.clear()



        instance.oldValues.map { keyValuePair ->


            var newValue = ReflectHelper.getValue(kclass, instance, keyValuePair.key);
            if (newValue != null) {

                if (insertFields != "") {
                    insertFields = insertFields + ","
                    insertData = insertData + ","
                }
                insertFields += "`" + keyValuePair.key + "`";
                insertData += "'" + newValue.toString() + "'";

                insertValues.put(keyValuePair.key, newValue.toString())
            }


        }

    }

    fun parseData(): String {
        return insertData;
    }

    fun parseInsertField(): String {
        return insertFields;
    }

    fun parseInsert(): String {
        return "INSERT"
    }

    companion object {
        var insertSqlTemplate = "%INSERT% INTO %TABLE% (%FIELD%) VALUES (%DATA%) %COMMENT%"


    }
}