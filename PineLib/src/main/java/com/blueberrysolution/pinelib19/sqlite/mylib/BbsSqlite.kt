package com.blueberrysolution.pinelib19.sqlite.mylib

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.blueberrysolution.pinelib19.activity.A

/**
 * App ONCREATE
 *  override fun onCreate() {
super.onCreate()

initDatabase();
sqlite = BbsSqlite("driving_test.db")
}

fun initDatabase() {
val assistFile = AssistFile()
assistFile.initDatabase("driving_test")
}
 *
 * */
class BbsSqlite {
    var fileName = "";
    var version = 1;

    lateinit var sqlLiteHelper: SQLiteOpenHelper;
    lateinit var db: SQLiteDatabase;

    constructor(): this("sqlite.db"){ }

    constructor(fileName: String, version: Int = 1){
        this.fileName = fileName;
        this.version = version;

        connect();
    }

    fun connect(){
        sqlLiteHelper = MySqliteHelper(A.c(), fileName,version);
        db = sqlLiteHelper.writableDatabase

        A.db(db);
    }

}