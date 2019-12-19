package com.qinglianyun.eyepetizerkotlinstudy.services

import android.os.Environment
import com.qinglianyun.base.net.DownloadListener
import com.qinglianyun.base.utils.ToastUtils
import com.qinglianyun.base.utils.Utils
import com.qinglianyun.eyepetizerkotlinstudy.bean.db.VideoDbManager
import com.qinglianyun.eyepetizerkotlinstudy.bean.db.VideoTb
import com.qinglianyun.eyepetizerkotlinstudy.model.DownloadModel
import com.qinglianyun.eyepetizerkotlinstudy.utils.SharedPreferenceUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File

/**
 * Created by tang_xqing on 2019/12/5.
 */
class DownloadClient private constructor() {
    private val video_path: String = "VideoDownload"

    private val mModel: DownloadModel by lazy {
        DownloadModel()
    }

    private val mDownloadMap: HashMap<String, DownloadListener> = HashMap()

    fun dowloadVideoByUri(url: String) {
        if (mDownloadMap.containsKey(url)) {
            return
        }

        var name = url.hashCode().toString()
//        var i = url?.lastIndexOf('/');//一定是找最后一个'/'出现的位置
//        if (i != -1) {
//            delegeName = url.substring(i)
//        }

        var parentFile = Environment.getExternalStorageDirectory().absolutePath
        var fileNew = File(parentFile + File.separator + video_path, name)
        if (!fileNew.parentFile.exists()) {
            fileNew.parentFile.mkdir()
        }
        if(fileNew.exists()){
            fileNew.delete()
        }
        fileNew.createNewFile()

        var listener = object : DownloadListener {
            override fun onStart() {

            }

            override fun onProgress(progress: Long, total: Long) {
                println("下载进度 progress = $progress total = $total")
            }

            override fun onFinish(fPath: String) {
                ToastUtils.showShortInfo("下载完成")
                mDownloadMap.remove(url)
                // 更新数据库
                SharedPreferenceUtils.putVideoDownloadState(url, "finish")
                VideoDbManager.getInstance(Utils.getContext()).getVideoByUri(url)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        var temp = VideoTb(it.vid, it.videoBean, fPath)
                        VideoDbManager.getInstance(Utils.getContext()).updateVideo(temp)
                            .subscribeOn(Schedulers.io()).subscribe {}
                    }
            }

            override fun onError(code: Int, msg: String) {
                mDownloadMap.remove(url)
            }
        }

        /**
         * 下载完成，状态如何更新。
         * 点击下载：数据库记录信息
         */
        // 保存在SP文件中，下载中。下载完成后，更新数据库和SP文件
        mDownloadMap.put(url, listener)
        mModel.downloadFile(url, fileNew, listener)
    }

    companion object {
        private var client: DownloadClient? = null
        fun getInstance(): DownloadClient {
            if (null == client) {
                synchronized(DownloadClient::class.java) {
                    if (null == client) {
                        client = DownloadClient()
                    }
                }
            }
            return client!!
        }
    }
}