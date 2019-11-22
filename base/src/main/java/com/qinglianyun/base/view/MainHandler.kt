package com.qinglianyun.base.view

import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference

/**
 * Created by tang_xqing on 2019/11/6.
 */
class MainHandler(baseView: IBaseView) : Handler() {
    protected var mView: WeakReference<IBaseView>

    init {
        mView = WeakReference(baseView)
    }

    override fun handleMessage(msg: Message?) {
        var view: IBaseView? = mView.get()
        msg?.let { view?.handleMessage(it) }
    }
}