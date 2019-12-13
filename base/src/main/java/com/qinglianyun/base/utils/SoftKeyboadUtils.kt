package com.qinglianyun.base.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/** 软键盘工具类
 * Created by tang_xqing on 2019/11/28.
 */
object SoftKeyboadUtils{

    @SuppressLint("ServiceCast")
    fun openSoftKeyboard(ctx:Context, et:EditText){
        var im = ctx.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.showSoftInput(et,InputMethodManager.RESULT_SHOWN)
        im.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    fun closeSoftKeyboard(ctx:Context, et:EditText) {
        var im = ctx.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(et.windowToken,0)
    }

}