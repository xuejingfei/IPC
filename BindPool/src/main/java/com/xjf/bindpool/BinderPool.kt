package com.xjf.bindpool

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import java.util.concurrent.CountDownLatch

/**
 * Author：xuejingfei
 *
 * Description：
 *
 * Date：2021/7/2 08:12
 */
class BinderPool private constructor(private val mContext: Context) {
    companion object {
        val TAG = BinderPool::class.simpleName
        const val BINDER_COMPUTE = 0
        const val BINDER_SECURITY = 1
        const val BINDER_NONE = -1

        fun getInstance(context: Context): BinderPool {
            return lazy(LazyThreadSafetyMode.SYNCHRONIZED) { BinderPool(context) }.value
        }

    }

    var mConnectBinderPoolCountDownLatch: CountDownLatch? = null
    var mBinderPool: IBinderPool? = null

    private val mDeathRecipient: IBinder.DeathRecipient = object : IBinder.DeathRecipient {
        override fun binderDied() {
            mBinderPool?.asBinder()?.unlinkToDeath(this, 0)
            mBinderPool = null
            connectBinderService()
        }
    }

    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            TODO("Not yet implemented")
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mBinderPool = IBinderPool.Stub.asInterface(service)
            mBinderPool?.asBinder()?.linkToDeath(mDeathRecipient, 0)
        }

    }


    init {
        connectBinderService()
    }


    private fun connectBinderService() {
        mConnectBinderPoolCountDownLatch = CountDownLatch(1)
        val intent = Intent(mContext, BinderPoolService::class.java)
        mContext.bindService(intent,mServiceConnection,Context.BIND_AUTO_CREATE)
        try {
            mConnectBinderPoolCountDownLatch?.await()
        }catch (e:InterruptedException) {
            e.printStackTrace()
        }

    }


    public fun queryBinder(binderCode:Int):IBinder? {
        var binder:IBinder ?= null
        try {
            binder = mBinderPool?.queryBinder(binderCode)
        }catch (e:RemoteException) {
            e.printStackTrace()
        }
        return binder
    }





}