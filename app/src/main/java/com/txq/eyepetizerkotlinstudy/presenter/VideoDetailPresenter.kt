package com.txq.eyepetizerkotlinstudy.presenter

import android.content.Context
import android.os.Environment
import android.widget.Toast
import com.txq.base.net.DownloadListener
import com.txq.base.view.BasePresenter
import com.txq.eyepetizerkotlinstudy.MyApplication
import com.txq.eyepetizerkotlinstudy.model.DownloadModel
import com.txq.eyepetizerkotlinstudy.services.DownloadService
import com.txq.eyepetizerkotlinstudy.view.i.IVideoDetailView
import java.io.File

/**
 * Created by tang_xqing on 2019/11/26.
 */
class VideoDetailPresenter(view: IVideoDetailView) : BasePresenter<IVideoDetailView>(view) {
    private val TAG: String = VideoDetailPresenter::class.java.simpleName

    val mModel: DownloadModel by lazy {
        DownloadModel()
    }

    fun downloadFile(url: String): String {

        printLog("downloadFile() $url")
        var name = url.hashCode().toString()
        var i = url?.lastIndexOf('/');//一定是找最后一个'/'出现的位置
        if (i != -1) {
            name = url.substring(i)
        }

        var parentFile = Environment.getExternalStorageDirectory()
        var fileNew = File(parentFile, name)
        fileNew.createNewFile()

        /**
         * 读取写入文件的进度
         */
        mModel.downloadFile(url, fileNew, object : DownloadListener {
            override fun onStart() {
                printLog("onStart()")
            }

            override fun onProgress(progress: Long, total: Long) {
//                printLog("onProgress() $progress $total")
            }

            override fun onFinish(filePath: String) {
                Toast.makeText(
                    MyApplication.getInstance()?.applicationContext,
                    "下载完成",
                    Toast.LENGTH_SHORT
                ).show()
                printLog("onFinish() $filePath")
            }

            override fun onError(code: Int, msg: String) {
                printLog("onError() $msg")
            }
        })


        /**
         * 拦截器进度
         */
//        mModel.downloadFile1(url, fileNew, object : DownloadListener {
//            override fun onStart() {
//                printLog("onStart1()")
//            }
//
//            override fun onProgress(progress: Long, total: Long) {
////                printLog("onProgress() $progress $total")
//            }
//
//            override fun onFinish(filePath: String) {
//                printLog("onFinish1() $filePath")
//            }
//
//            override fun onError(code: Int, msg: String) {
//                printLog("onError1() $msg")
//            }
//        })

        return fileNew.absolutePath
    }

    /**
     * 下载文件获取下载进度条两种方式：
     * 1）通过拦截器，获得写入内存的进度
     *   存在问题：已写入内存，但文件还没有写入完成，进度就完成了
     *      解决方法：将onFinish()方法移动到写入文件完成后回调
     *
     * 2）直接读取文件的写入进度（已写入内存）
     *   存在问题：从点击下载到开始写入文件之间存在一定的时间，会导致onStart()方法回调较晚
     *      解决方法：Listener没有onStart()方法，只有onProgress()
     */

    fun downloadFileToService(ctx:Context,url:String){
        DownloadService.getInstance(ctx,url)
    }

    private fun printLog(content: String) {
        println("$TAG --> $content  threadName = ${Thread.currentThread().name}")
    }
}