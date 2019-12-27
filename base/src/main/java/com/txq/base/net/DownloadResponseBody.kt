package com.txq.base.net

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*

/**
 * Created by tang_xqing on 2019/12/2.
 */
class DownloadResponseBody(responseBody: ResponseBody, listener: DownloadFileListener) :
    ResponseBody() {
    private var mResponseBody: ResponseBody
    private var mListener: DownloadFileListener ?= null
    private var bufferedSource: BufferedSource? = null

    init {
        mResponseBody = responseBody
        mListener = listener
    }

    override fun contentLength(): Long {
        return mResponseBody.contentLength()
    }

    override fun contentType(): MediaType? {
        return mResponseBody.contentType()
    }

    override fun source(): BufferedSource {

        if (null == bufferedSource) {
            bufferedSource = Okio.buffer(getSource(mResponseBody.source()))
        }
        return bufferedSource!!
    }

    private fun getSource(source: BufferedSource): Source {
        return object : ForwardingSource(source) {
            var totalBytesRead = 0L

            override fun read(sink: Buffer, byteCount: Long): Long {
                var read = super.read(sink, byteCount)
                if (totalBytesRead != mResponseBody.contentLength()) {
                    mListener?.let {
                        totalBytesRead = totalBytesRead.plus(if (read == -1L) 0 else read)
                        it.onProgress(
                            totalBytesRead,
                            mResponseBody.contentLength(),
                            totalBytesRead == mResponseBody.contentLength()
                        )
                    }
                }else{
                    mListener = null
                }
                return read
            }
        }
    }
}
