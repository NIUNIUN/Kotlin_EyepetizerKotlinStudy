package com.qinglianyun.eyepetizerkotlinstudy.utils

import android.content.Context
import android.content.SharedPreferences
import com.qinglianyun.base.utils.Utils

/**
 * Created by tang_xqing on 2019/12/5.
 */
object SharedPreferenceUtils {
    private val mCtx: Context by lazy { Utils.getContext() }
    private val HEAD_IMG: String = "head_img"  // 头像

    private val SP_NAME: String = "video_sp"
    private var sp: SharedPreferences

    init {
        sp = mCtx.getSharedPreferences(
            SP_NAME, Context.MODE_PRIVATE)
    }

    fun putVideoDownloadState(key: String, download: String) {
        sp.edit().putString(key, download).apply()
    }

    fun getVideoDownloadStateByKey(key: String): String? {
        return sp.getString(key, null)
    }

    fun putHeaderImage(uri: String) {
        sp.edit().putString(HEAD_IMG, uri).apply()
    }

    fun getHeaderImage(): String {
        return sp.getString(HEAD_IMG, "")
    }
}