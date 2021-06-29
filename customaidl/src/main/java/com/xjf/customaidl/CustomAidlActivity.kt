package com.xjf.customaidl

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.xjf.customaidl.aidl.Book
import kotlinx.android.synthetic.main.activity_custom_aidl.*

class CustomAidlActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_aidl)
        BookServiceManager.getInstance().bindService(this)
        tv_add.setOnClickListener {
            BookServiceManager.getInstance().addBook(Book("数学", "数学书"))
            BookServiceManager.getInstance().addBook(Book("语文", "语文书"))
        }


        tv_get.setOnClickListener {
            for(book in BookServiceManager.getInstance().books) {
                Log.d("xjf",book.name)
            }
        }
    }



    companion object {
        @JvmStatic
         fun startActivity(context: Context){
            val intent = Intent(context,CustomAidlActivity::class.java)
            context.startActivity(intent)
        }
    }
}
