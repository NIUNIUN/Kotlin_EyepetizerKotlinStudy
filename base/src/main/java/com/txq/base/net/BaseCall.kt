package com.txq.base.net

import com.txq.base.bean.ErrorMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by tang_xqing on 2019/11/26.
 */
class BaseCallback<T>(callback: ICallback<T>) : Callback<T> {
    var mCallback: ICallback<T>

    init {
        if (null == callback) {
            throw NullPointerException("callback is NULL")
        }
        mCallback = callback
    }

    override fun onFailure(call: Call<T>, t: Throwable) {

        mCallback.onError(ErrorMessage(-1, t.message as String))
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        mCallback.onSuccess(response.body() as T)
    }
}