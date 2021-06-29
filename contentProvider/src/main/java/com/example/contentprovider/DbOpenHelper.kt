package com.example.contentprovider

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Author：xuejingfei
 *
 * Description：
 *
 * Date：2021/6/27 17:51
 */
class DbOpenHelper(context: Context):SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION){


    companion object {
        val DB_NAME ="book_provider.db"
        val BOOK_TABLE_NAME ="book"
        val USER_TABLE_NAME ="user"
        val DB_VERSION = 1
        val CREATE_BOOK_TABLE ="CREATE TABLE IF NOT EXISTS $BOOK_TABLE_NAME (_id INTEGER PRIMARY KEY, name TEXT)"
        val CREATE_USER_TABLE ="CREATE TABLE IF NOT EXISTS $USER_TABLE_NAME (_id INTEGER PRIMARY KEY, name TEXT, sex INT)"

    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_BOOK_TABLE)
        db?.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}