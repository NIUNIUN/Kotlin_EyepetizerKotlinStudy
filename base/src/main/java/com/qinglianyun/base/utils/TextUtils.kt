package com.qinglianyun.base.utils

import android.text.TextUtils
import android.widget.TextView

/**
 * Created by tang_xqing on 2019/11/25.
 */
object TextUtils {
    val STR_DEFAULT: String = "--"

    fun setText(tv: TextView, str: String) {
        setText(tv, str, STR_DEFAULT)
    }

    fun setText(tv: TextView, str: String, default: String?) {
        tv?.let {
            if (TextUtils.isEmpty(str) || str.equals("null") || str.equals("NULL")) {
                tv.setText(default ?: STR_DEFAULT)
            } else {
                tv.setText(str)
            }
        }
    }

}