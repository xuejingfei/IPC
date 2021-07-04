package com.xjf.bindpool

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * Author：xuejingfei
 *
 * Description：
 *
 * Date：2021/7/3 12:35
 */
class BinderPoolActivity:AppCompatActivity() {
    companion object {
        val TAG = BinderPoolActivity::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_binder_pool)
        doWork()
    }



    private fun doWork() {
        val binderPool: BinderPool = BinderPool.getInstance(baseContext)
        val securityBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY)
        val security = ISecurityCenter.Stub.asInterface(securityBinder)
        Log.d(TAG,security.decrypt("xjf"))


        val computerBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE)
        val computer = ICompute.Stub.asInterface(computerBinder)
        Log.d(TAG,computer.add(1,2).toString())
    }

}