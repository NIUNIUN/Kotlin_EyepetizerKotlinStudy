package com.txq.base.utils

import android.text.TextUtils
import android.widget.TextView

/**
 * Created by tang_xqing on 2019/11/25.
 */
object TextUtils {
    val STR_DEFAULT: String = "--"

    fun isEmpty(str: String?): Boolean {
        return TextUtils.isEmpty(str)
    }

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

        var str:Float = 1.243f
        str.hashCode()
    }

    /**
     * 为控件设置默认值
     */
    fun setDefaultForTextView(default: String?, vararg tv: TextView) {
        var defaultValue = if (isEmpty(default)) STR_DEFAULT else default
        tv.filter { null != it }
            .forEach { it.setText(defaultValue) }
    }
}