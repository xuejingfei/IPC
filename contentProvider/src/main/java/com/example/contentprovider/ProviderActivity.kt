package com.example.contentprovider

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log

/**
 * Author：xuejingfei
 *
 * Description：
 *
 * Date：2021/6/27 17:37
 */
class ProviderActivity:AppCompatActivity() {
    companion object{
       val TAG = ProviderActivity::class.simpleName
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider)
        val bookUri = Uri.parse("content://com.example.contentprovider.book.provider/book")
        val contentValues = ContentValues()
        contentValues.put("_id",5)
        contentValues.put("name","程序设计的艺术")
        contentResolver.insert(bookUri,contentValues)
        val bookCursor = contentResolver.query(bookUri, arrayOf("_id","name"),null,null,null)
        while (bookCursor!!.moveToNext()) Log.d(TAG,"_id$bookCursor.getInt(0),name:$bookCursor.getString(1)")
        bookCursor.close()


        val userUri = Uri.parse("content://com.example.contentprovider.book.provider/user")
        val userCursor = contentResolver.query(userUri, arrayOf("_id","name","sex"),null,null,null)
        while (userCursor!!.moveToNext()) Log.d(TAG,"_id:$userCursor.getInt(0),name:$userCursor.getString(1)sex:${userCursor.getInt(2)}")
        userCursor.close()

    }
}