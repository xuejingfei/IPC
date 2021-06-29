package com.example.messenager

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import com.example.messenager.MessengerActivity.Companion.MSG_FROM_CLIENT

/**
 * Author：xuejingfei
 *
 * Description：
 *
 * Date：2021/6/27 10:44
 */
class AidlService:Service() {

    companion object{
        val TAG = AidlService::class.simpleName
        const val MSG_FROM_SERVICE = 2
    }

    class MessengerHandler:Handler(){
        override fun handleMessage(msg: Message) {
            when(msg.what) {
                 MSG_FROM_CLIENT -> {
                     Log.d(TAG,"receive msg from Client:" + msg.data.getString("msg"))
                     //服务端向客户端发送消息
                     val client = msg.replyTo
                     val messenger = Message.obtain(null,MSG_FROM_SERVICE)
                     val bundle = Bundle()
                     bundle.putString("reply","嗯，你的消息已经收到，稍后会回复您")
                     messenger.data = bundle
                     try {
                         client.send(messenger)
                     }catch (e:RemoteException){
                         e.printStackTrace()
                     }

                 }
                 else ->{
                     super.handleMessage(msg)
                 }
            }
        }
    }


    private val mMessenger:Messenger =  Messenger(MessengerHandler())

    override fun onBind(intent: Intent?): IBinder? {
        return mMessenger.binder
    }

}