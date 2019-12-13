package com.qinglianyun.base.utils

import android.os.Build

/**
 * Created by tang_xqing on 2019/12/13.
 */
class BuildUtils {

    companion object {
        fun getSDKVersion() = Build.VERSION.SDK_INT

        fun isMarshmallow() = getSDKVersion() >= Build.VERSION_CODES.M

        fun isNougat() = getSDKVersion() >= Build.VERSION_CODES.N

        fun isO() = getSDKVersion() >= Build.VERSION_CODES.O

        fun isP() = getSDKVersion() >= Build.VERSION_CODES.P
    }
}