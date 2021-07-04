package com.example.messenager

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import com.example.messenager.AidlService.Companion.MSG_FROM_SERVICE

/**
 * Author：xuejingfei
 *
 * Description：
 *
 * Date：2021/6/27 08:08
 */
class MessengerActivity: Activity() {

    companion object{
        const val MSG_FROM_CLIENT = 0
    }


    var mService: Messenger? = null


    class MessengerHandler:Handler() {
        override fun handleMessage(msg: Message) {
            when(msg.what){
                MSG_FROM_SERVICE -> {
                    Log.d(AidlService.TAG,"receive msg from Client:" + msg.data.getString("reply"))
                }
            }
            super.handleMessage(msg)
        }
    }


    private var serviceConnection : ServiceConnection =object:ServiceConnection{
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mService = Messenger(service)
            val msg = Message.obtain(null,MSG_FROM_CLIENT)
            val bundle = Bundle()
            bundle.putString("msg","hello,this is client")
            msg.data = bundle
            msg.replyTo = Messenger(MessengerHandler())
            try {
                mService?.send(msg)
            }catch (exception:RemoteException) {
                exception.printStackTrace()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messenger)
        val intent = Intent(this,AidlService::class.java)
        bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        unbindService(serviceConnection)
        super.onDestroy()
    }


}