package com.xjf.ipc

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.contentprovider.ProviderActivity
import com.example.messenager.MessengerActivity
import com.xjf.customaidl.CustomAidlActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * description: 专家个人信息界面
 * @author xuejingfei
 * create at 19-6-17
 */
class MainActivity :Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        aidl.setOnClickListener {
            startActivity(Intent(this,AidlActivity::class.java))
        }

        custom_aidl.setOnClickListener {
            startActivity(Intent(this,CustomAidlActivity::class.java))
        }

        messenger.setOnClickListener{
            startActivity(Intent(this, MessengerActivity::class.java))
        }

        contentProvider.setOnClickListener {
            startActivity(Intent(this, ProviderActivity::class.java))
        }


    }
}