package com.txq.eyepetizerkotlinstudy

import android.app.Application
import android.arch.lifecycle.ProcessLifecycleOwner
import android.support.multidex.MultiDex
import com.txq.base.utils.Utils
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule

/**
 * Created by tang_xqing on 2019/11/27.
 */
open class MyApplication : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        // 可以在这里进入一些初始化SDK的操作，例如Retrofit、数据库

        // Kodein自带的AndroidModule
        import(androidModule(this@MyApplication))
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        Utils.initContext(this)
        MultiDex.install(this)

        // ProcessLifecycleOwner感知整个应用生命周期
        ProcessLifecycleOwner.get()
            .lifecycle.addObserver(ObserverClass(ProcessLifecycleOwner.get().lifecycle))
    }

    companion object {
        private var mContext: Application? = null
        fun getInstance(): Application? {
            return mContext
        }
    }


}