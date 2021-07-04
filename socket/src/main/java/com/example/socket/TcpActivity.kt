package com.example.socket

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.io.*
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.*

/**
 * Author：xuejingfei
 *
 * Description：
 *
 * Date：2021/7/4 09:23
 */

class TcpActivity : Activity() {
    var mClientSocket: Socket? = null
    var mPrintWriter: PrintWriter? = null
    var messageText:TextView ?= null
    var mSendBtn:Button ?= null


    companion object {
        val TAG = TcpActivity::class.simpleName
        const val MESSAGE_RECEIVE_NEW_MSG = 1
        const val MESSAGE_SOCKET_CONNECTED =2
    }

    private val mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what) {
                MESSAGE_RECEIVE_NEW_MSG -> messageText?.text = messageText?.text.toString() + msg.obj.toString()
                MESSAGE_SOCKET_CONNECTED -> mSendBtn?.isEnabled = true
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_socket)
        messageText = findViewById(R.id.message_text)
        mSendBtn = findViewById(R.id.send_btn)
        val intent = Intent(this, TCPServerService::class.java)
        startService(intent)
        Thread {
            connectTCPService()
        }.start()
    }

    override fun onDestroy() {
        mClientSocket?.shutdownInput()
        mClientSocket?.close()
        super.onDestroy()
    }


    @SuppressLint("SimpleDateFormat")
    private fun connectTCPService() {
        var socket: Socket? = null
        while (socket == null) {
            try {
                socket = Socket("localhost", 8688)
                mClientSocket = socket
                mPrintWriter = PrintWriter(BufferedWriter(OutputStreamWriter(mClientSocket?.getOutputStream())),true)
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED)
                Log.d(TAG,"connect server success")
            }catch (e:IOException) {
                SystemClock.sleep(1000)
                e.printStackTrace()
                Log.d(TAG,"connect tcp server failed, retry...")
            }
        }

        try {
            val bufferReader = BufferedReader(InputStreamReader(socket.getInputStream()))
            while (!isFinishing) {
                val msg = bufferReader.readLine()
                Log.d(TAG,"receive :$msg")
                if(msg !=null) {
                    val time = SimpleDateFormat("(HH:mm:ss)").format(Date(System.currentTimeMillis()))
                    val showMessage = "server $time $msg\n"
                    mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG,showMessage).sendToTarget()
                }
            }

            mPrintWriter?.close()
            bufferReader.close()
            socket.close()
        }catch (e:IOException) {
            e.printStackTrace()
        }
    }


}