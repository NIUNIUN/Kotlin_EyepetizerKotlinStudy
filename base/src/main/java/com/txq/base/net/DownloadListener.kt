package com.txq.base.net

/**  基于Retrofit文件写入：下载文件监听
 * Created by tang_xqing on 2019/12/2.
 */
interface DownloadListener {

    fun onStart()
    fun onProgress(progress: Long, total: Long)
    fun onFinish(filePath: String)
    fun onError(code: Int, msg: String)
}