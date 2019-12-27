package com.txq.base.net

/** 基于Retrofit的拦截器：下载监听
 * Created by tang_xqing on 2019/12/2.
 */
interface DownloadFileListener {
    fun onProgress(progress: Long, totalLen: Long, finish: Boolean)
}