package com.txq.base.view

import android.os.Message

/**
 * Created by tang_xqing on 2019/11/6.
 */
interface IBaseView {
    fun handleMessage(message:Message)
    fun sendMessage(what:Int,obj:Any)
    fun sendMessage(what: Int,obj: Any,arg1:Int,arg2:Int)
    fun sendMessage(what: Int,obj: Any,delayTime:Long)
}