package com.txq.eyepetizerkotlinstudy.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder

/**
 * Created by tang_xqing on 2019/12/5.
 */
class DownloadService: Service() {
    private var mClient: DownloadClient? = null

    /**
     * Service第一次创建时调用，后面多次调用ctx.startService()，都不会执行此方法
     */
    override fun onCreate() {
        println("下载进度 onCreate ")
        mClient = DownloadClient.getInstance()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    /**
     * 每调用一次ctx.startService()，都会调用此方法
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("下载进度 onStartCommand ")
        intent?.let {
            var uri = it.getStringExtra(EXTRA_KEY_URI)
            mClient?.let {
                it.dowloadVideoByUri(uri)
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        const val EXTRA_KEY_URI = "download_uri"

        fun getInstance(ctx: Context, downloadUri: String) {
            var intent: Intent = Intent(ctx, DownloadService::class.java)
            intent.putExtra(EXTRA_KEY_URI, downloadUri)
            ctx.startService(intent)
        }
    }
}