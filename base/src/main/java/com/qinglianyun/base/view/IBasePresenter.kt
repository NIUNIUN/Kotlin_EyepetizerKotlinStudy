package com.qinglianyun.base.view

/**
 * Created by tang_xqing on 2019/11/6.
 */
interface IBasePresenter {
    fun onViewRemove()
    fun onViewCreate()
    fun onViewPause()
    fun onViewStart()
    fun onViewRestart()
    fun onViewResume()
    fun onViewStop()
    fun onViewDestory()
    fun sendMessage(what: Int, obj: Any)
    fun sendMessage(what: Int,obj: Any,arg1:Int,arg2:Int)
    fun sendMessage(what: Int, obj: Any, delayTime: Long)
}