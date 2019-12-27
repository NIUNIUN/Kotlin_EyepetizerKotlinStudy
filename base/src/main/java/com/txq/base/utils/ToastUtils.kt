package com.txq.base.utils

import android.content.Context
import android.widget.Toast
import java.lang.Exception

/**
 * Created by tang_xqing on 2019/12/2.
 */
object ToastUtils {
    private val mContenxt: Context by lazy {
        Utils.getContext()
    }

    fun showShortInfo(content: String) {
        Toast.makeText(mContenxt, content, Toast.LENGTH_SHORT).show()
    }

    fun showLongInfo(content: String) {
        Toast.makeText(mContenxt, content, Toast.LENGTH_LONG).show()
    }

    fun showSafeShortInfo(content: String) {
        try {
            showShortInfo(content)
        } catch (e: Exception) {

        }
    }

    fun showSafeLongInfo(content: String) {
        try {
            showLongInfo(content)
        } catch (e: Exception) {

        }
    }
}