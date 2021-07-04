package com.xjf.bindpool

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Author：xuejingfei
 *
 * Description：
 *
 * Date：2021/7/2 08:32
 */
class BinderPoolService :Service(){
    private val mBinderPoolImpl = BinderPoolImpl()

    override fun onBind(intent: Intent?): IBinder? {
        return mBinderPoolImpl
    }

}