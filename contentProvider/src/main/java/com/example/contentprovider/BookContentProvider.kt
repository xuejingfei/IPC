package com.example.contentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.util.Log
import java.lang.IllegalArgumentException

/**
 * Author：xuejingfei
 *
 * Description：
 *
 * Date：2021/6/27 17:21
 */
class BookContentProvider : ContentProvider() {
    var mContext: Context? = null
    var mDb: SQLiteDatabase? = null

    companion object {
        val TAG = BookContentProvider::class.simpleName
        val AUTHORITY = "com.example.contentprovider.book.provider"
        val BOOK_CONTENT_URI = "content://$AUTHORITY/book"
        val USER_CONTENT_URI = "content://$AUTHORITY/user"
        val BOOK_URI_CODE = 0
        val USER_URI_CODE = 1
        val uriMatcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    }

    init {
        uriMatcher.addURI(AUTHORITY, BOOK_CONTENT_URI, BOOK_URI_CODE)
        uriMatcher.addURI(AUTHORITY, USER_CONTENT_URI, USER_URI_CODE)

    }

    override fun onCreate(): Boolean {
        Log.d(TAG, "onCreate,current Thread:${Thread.currentThread().name}")
        mContext = context
        initProviderData()
        return true
    }

    private fun initProviderData() {
        mDb = mContext?.let { DbOpenHelper(it).writableDatabase }
        mDb?.execSQL("delete from ${DbOpenHelper.BOOK_TABLE_NAME}")
        mDb?.execSQL("delete from ${DbOpenHelper.USER_TABLE_NAME}")
        mDb?.execSQL("insert into book values(3,'Android');")
        mDb?.execSQL("insert into book values(4,'IOS');")
        mDb?.execSQL("insert into book values(5,'Html5');")
        mDb?.execSQL("insert into user values(1,'jake',1);")
        mDb?.execSQL("insert into user values(2,'jasmine',0);")

    }


    fun getTableName(uri: Uri): String? {
        var tableName: String? = null
        when (uriMatcher.match(uri)) {
            BOOK_URI_CODE -> tableName = DbOpenHelper.BOOK_TABLE_NAME
            USER_URI_CODE -> tableName = DbOpenHelper.USER_TABLE_NAME
        }

        return tableName
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        Log.d(TAG, "query,current Thread:${Thread.currentThread().name}")
        val tableName: String = getTableName(uri) ?: throw IllegalArgumentException("Unsupported URI$uri")
        return mDb?.query(tableName,projection,selection,selectionArgs,null,null,sortOrder,null)
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.d(TAG, "insert,current Thread:${Thread.currentThread().name}")
        val tableName: String = getTableName(uri) ?: throw IllegalArgumentException("Unsupported URI$uri")
        mDb?.insert(tableName,null,values)
        mContext?.contentResolver?.notifyChange(uri,null)
        return uri
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        Log.d(TAG, "update,current Thread:${Thread.currentThread().name}")
        val tableName: String = getTableName(uri) ?: throw IllegalArgumentException("Unsupported URI$uri")
        val row = mDb?.update(tableName,values,selection,selectionArgs)
        if(row !=null && row>0) {
            mContext?.contentResolver?.notifyChange(uri,null)
        }
        return row ?:0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        Log.d(TAG, "delete,current Thread:${Thread.currentThread().name}")
        val table = getTableName(uri)?: throw IllegalArgumentException("Unsupported URI$uri")
        val count = mDb?.delete(table,selection,selectionArgs)

        if (count != null && count>0) {
            mContext?.contentResolver?.notifyChange(uri,null)
        }
        return count ?: 0
    }

    override fun getType(uri: Uri): String? {
        Log.d(TAG, "getType,current Thread:${Thread.currentThread().name}")
        return null
    }
}