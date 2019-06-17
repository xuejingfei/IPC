package com.xjf.ipc

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val mCallBackInterface = object : NotifyCallBackInterface.Stub(){
        override fun notifyNewBook(book: Book?) {
            Log.d("xjf",book?.name)
        }
    }

    private val mCallBackInterface1 = object : NotifyCallBackInterface.Stub(){
        override fun notifyNewBook(book: Book?) {
            Log.d("xjf",book?.name)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BookServiceManager.getInstance().bindService(this)
        BookServiceManager.getInstance().registerListener(mCallBackInterface)
        BookServiceManager.getInstance().registerListener(mCallBackInterface1)
        tv_add.setOnClickListener {
            BookServiceManager.getInstance().addBook(Book("数学","数学书"))
            BookServiceManager.getInstance().addBook(Book("语文","语文书"))
        }


        tv_get.setOnClickListener {
            for(book in BookServiceManager.getInstance().books) {
                Log.d("xjf",book.name)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        BookServiceManager.getInstance().unRegisterListener(mCallBackInterface)
        BookServiceManager.getInstance().unBindService(this)
    }
}
