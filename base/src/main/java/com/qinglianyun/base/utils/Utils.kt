package com.qinglianyun.base.utils

import android.content.Context

/**
 * Created by tang_xqing on 2019/12/2.
 */
object Utils {

    private var mContext: Context? = null
    fun init(context: Context) {
        mContext = context.applicationContext
    }

    fun getContext(): Context {
        return mContext!!
    }
}