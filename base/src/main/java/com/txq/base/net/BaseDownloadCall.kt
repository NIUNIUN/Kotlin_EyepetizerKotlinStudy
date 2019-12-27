package com.txq.base.net

import com.txq.base.comm.CommError
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.Exception

/**
 * Created by tang_xqing on 2019/12/2.
 */
class BaseDownloadCall(file: File, listener: DownloadListener) : Callback<ResponseBody> {
    var mListener: DownloadListener
    var mFile: File

    init {
        if (null == listener) {
            throw  NullPointerException("downloadListener is NULL")
        }
        if (null == file) {
            throw  NullPointerException("file is NULL")
        }
        mListener = listener
        mFile = file
    }

    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
        mListener.onError(CommError.DOWNLOAD_ERR_CODE, CommError.DOWNLOAD_ERR_MSG)
    }

    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
        response.body()?.let {
            writeToFile(it.byteStream(), it.contentLength())
        }
    }

    private fun writeToFile(inputStream: InputStream, totalLen: Long) {
        mListener.onStart()
        var fos: BufferedOutputStream? = null
        if (!mFile.exists()) {
            mFile.parentFile.mkdir()
            mFile.createNewFile()
        }
        try {
            fos = BufferedOutputStream(FileOutputStream(mFile))
            var b = ByteArray(1024)
            var lenth = inputStream.read(b)
            var tempProgress = 0
            while (lenth != -1) {
                fos.write(b, 0, lenth)
                fos.flush()
                lenth = inputStream.read(b)
                tempProgress = tempProgress.plus(lenth)
                mListener.onProgress(tempProgress.toLong(), totalLen)
            }
            fos.close()
            inputStream.close()
            mListener.onFinish(mFile.absolutePath)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (null != inputStream) inputStream.close()
            if (null != fos) fos.close()
        }
    }
}