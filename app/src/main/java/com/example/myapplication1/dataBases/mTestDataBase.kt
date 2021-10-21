package com.example.myapplication1.dataBases

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class mTestDataBase(context: Context, name: String, version: Int):
    SQLiteOpenHelper(context, name, null, version) {

    private val createTestTable = ""

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createTestTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}