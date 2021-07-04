package com.xjf.bindpool

/**
 * Author：xuejingfei
 *
 * Description：
 *
 * Date：2021/7/2 08:11
 */
class ComputeImp: ICompute.Stub() {
    override fun add(a: Int, b: Int): Int {
        return a+b
    }
}