package com.xjf.bindpool

import android.os.IBinder

/**
 * Author：xuejingfei
 *
 * Description：
 *
 * Date：2021/7/3 12:30
 */
class BinderPoolImpl :IBinderPool.Stub() {
    override fun queryBinder(binderCode: Int): IBinder? {
        var iBinder:IBinder? = null
        when(binderCode){
            BinderPool.BINDER_SECURITY -> iBinder = SecurityCenterImpl()
            BinderPool.BINDER_COMPUTE -> iBinder = ComputeImp()
        }

        return iBinder
    }
}