package com.example.socket

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.io.*
import java.net.ServerSocket
import java.net.Socket
import java.util.*

/**
 * Author：xuejingfei
 *
 * Description：
 *
 * Date：2021/7/4 08:50
 */
class TCPServerService :Service() {
    companion object {
        val TAG = TCPServerService::class.simpleName
        val messages = arrayOf(
            "您好啊",
            "请问你叫什么名字",
            "今天北京天气不错啊，shy",
            "你知道吗？我可是和多个人同时聊天"
        )
    }


    public var mIsServiceDestroyed = false


    override fun onCreate() {
        super.onCreate()
        Thread(TcpServer()).run()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        mIsServiceDestroyed = true
        super.onDestroy()
    }


   inner class TcpServer:Runnable {

        override fun run() {
            var serverSocket:ServerSocket ?=null
            try {
                serverSocket = ServerSocket(8688)
            }catch (e:IOException) {
                Log.e(TAG,"establish server failed,port:8688")
                e.printStackTrace()
                return
            }
            while(!mIsServiceDestroyed) {
                try {
                    val client = serverSocket.accept()
                    Log.d(TAG,"accept")
                    Thread {
                        responseClient(client)
                    }.start()
                }catch (e:IOException) {
                    e.printStackTrace()
                }
            }
        }
    }



    private fun responseClient(client:Socket) {
        val inReader = BufferedReader(InputStreamReader(client.getInputStream()))
        val outWriter = PrintWriter(BufferedWriter(OutputStreamWriter(client.getOutputStream())))
        outWriter.println("欢迎来到聊天室")
        while (!mIsServiceDestroyed) {
            val str = inReader.readLine()
            Log.d(TAG, "msg from client:$str")
            if (str ==null) {
                break
            }
            val i = Random().nextInt(messages.size)
            val msg = messages[i]
            outWriter.println(msg)
            Log.d(TAG,"send:$msg")

        }

        Log.d(TAG,"client is quit.")

        inReader.close()
        outWriter.close()
        client.close()
    }

}