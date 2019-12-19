package com.qinglianyun.eyepetizerkotlinstudy

import android.app.Application
import android.arch.lifecycle.ProcessLifecycleOwner
import android.support.multidex.MultiDex
import com.qinglianyun.base.utils.Utils

/**
 * Created by tang_xqing on 2019/11/27.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        mContext = this
        Utils.initContext(this)
        MultiDex.install(this)

        // ProcessLifecycleOwner感知整个应用生命周期
        ProcessLifecycleOwner.get().lifecycle.addObserver(ObserverClass(ProcessLifecycleOwner.get().lifecycle))
    }

    companion object {
        private var mContext: Application? = null
        fun getInstance(): Application? {
            return mContext
        }
    }
}