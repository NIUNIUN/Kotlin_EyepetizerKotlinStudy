package com.qinglianyun.base.utils

import android.util.Log

/**
 * Created by tang_xqing on 2019/12/13.
 */
class LogUtils {
    companion object {
        var TAG = LogUtils::class.java.simpleName
        var debugFlag = true

        fun e(msg: String, tag: String = TAG) {
            if (debugFlag) Log.e(tag, msg)
        }

        fun d(msg: String, tag: String = TAG) {
            if (debugFlag) Log.d(tag, msg)
        }

        fun i(msg: String, tag: String = TAG) {
            if (debugFlag) {
                Log.i(tag, msg)
            }
        }
    }
}