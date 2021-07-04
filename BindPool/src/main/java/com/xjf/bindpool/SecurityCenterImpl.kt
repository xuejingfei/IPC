package com.xjf.bindpool

/**
 * Author：xuejingfei
 *
 * Description：
 *
 * Date：2021/7/2 08:02
 */
class SecurityCenterImpl: ISecurityCenter.Stub(){

    companion object {
        const val SECRET_CODE = "^"
    }
    override fun encrypt(content: String?): String?{
        return content + SECRET_CODE
    }

    override fun decrypt(password: String?): String?{
        return password
    }

}