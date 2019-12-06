package com.qinglianyun.base.net

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody

/**
 * Created by tang_xqing on 2019/12/2.
 */
class DownloadInterceptor(listener: DownloadFileListener) : Interceptor {
    private var mListener: DownloadFileListener

    init {
        mListener = listener
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var proceed = chain.proceed(chain.request())
        return proceed.newBuilder()
            .body(DownloadResponseBody(proceed.body() as ResponseBody, mListener))
            .build()
    }
}