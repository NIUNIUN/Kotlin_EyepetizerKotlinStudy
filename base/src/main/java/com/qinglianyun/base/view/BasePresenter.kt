package com.qinglianyun.base.view

/**
 * Created by tang_xqing on 2019/11/6.
 */
open class BasePresenter<T : IBaseView>(val view: T) : IBasePresenter {
    var baseView: T? = null
    init {
        baseView = view
    }

    override fun onViewRemove() {
        baseView = null
    }

    override fun onViewCreate() {

    }

    override fun onViewPause() {
    }

    override fun onViewStart() {
    }

    override fun onViewRestart() {
    }

    override fun onViewResume() {
    }

    override fun onViewStop() {
    }

    override fun onViewDestory() {
    }

    override fun sendMessage(what: Int, obj: Any) {
        baseView?.sendMessage(what, obj) ?: return
    }

    override fun sendMessage(what: Int, obj: Any, arg1: Int, arg2: Int) {
        baseView?.sendMessage(what, obj, arg1, arg2) ?: return
    }

    override fun sendMessage(what: Int, obj: Any, delayTime: Long) {
        baseView?.sendMessage(what, obj, delayTime) ?: return
    }
}