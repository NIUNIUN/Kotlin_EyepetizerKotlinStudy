package com.qinglianyun.eyepetizerkotlinstudy

import android.app.Application
import android.support.multidex.MultiDex
import com.qinglianyun.base.utils.Utils

/**
 * Created by tang_xqing on 2019/11/27.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        mContext = this
        Utils.init(this)
        MultiDex.install(this)
    }

    companion object {
        private var mContext: Application? = null
        fun getInstance(): Application? {
            return mContext
        }
    }
}