package com.qinglianyun.base.net

import com.qinglianyun.base.bean.ErrorMessage

/**
 * Created by tang_xqing on 2019/11/26.
 */
interface ICallback<T> {
    fun onSuccess(result: T)
    fun onError(error: ErrorMessage)
}