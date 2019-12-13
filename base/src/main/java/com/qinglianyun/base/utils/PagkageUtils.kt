package com.qinglianyun.base.utils

import android.content.pm.PackageManager

/**
 * Created by tang_xqing on 2019/12/13.
 */
class PagkageUtils {
    companion object {
        private val mContext by lazy { Utils.getContext() }

        fun getPackageName() = mContext.packageName

        fun getPackageCodePath() = mContext.packageCodePath

        fun getPackageVersionName() =
            getPackageInfo().versionName

        fun getPackageInfo() =
            mContext.packageManager.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES)

        fun getPackageVersion(): Any {
            if (BuildUtils.isP()) {
                return getPackageInfo().longVersionCode
            } else {
                return getPackageInfo().versionCode
            }
        }
    }
}