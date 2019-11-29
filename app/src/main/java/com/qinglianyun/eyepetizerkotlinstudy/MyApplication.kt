package com.qinglianyun.eyepetizerkotlinstudy

import android.app.Application
import android.support.multidex.MultiDex

/**
 * Created by tang_xqing on 2019/11/27.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }
}