package com.blueberrysolution.pinelib19.sqlite.mylib

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// 表的名字

internal class MySqliteHelper(context: Context, sqlite_name: String, version: Int) : SQLiteOpenHelper(context, sqlite_name, null, version) {




    override fun onCreate(db: SQLiteDatabase?) {

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


}