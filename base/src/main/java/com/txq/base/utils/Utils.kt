package com.txq.base.utils

import android.content.Context

/**
 * Created by tang_xqing on 2019/12/2.
 */
class Utils {

    companion object {
        private var mContext: Context? = null

        fun initContext(context: Context) {
            mContext = context.applicationContext
        }

        fun getContext(): Context {
            return mContext!!
        }
    }
}