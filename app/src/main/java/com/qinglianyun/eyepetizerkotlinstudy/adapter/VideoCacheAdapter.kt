package com.qinglianyun.eyepetizerkotlinstudy.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.qinglianyun.base.adapter.BaseClickAdapter
import com.qinglianyun.base.utils.TextUtils
import com.qinglianyun.base.utils.ToastUtils
import com.qinglianyun.eyepetizerkotlinstudy.R
import com.qinglianyun.eyepetizerkotlinstudy.bean.db.VideoTb
import com.qinglianyun.eyepetizerkotlinstudy.services.DownloadService
import com.qinglianyun.base.utils.GlideUtils
import com.qinglianyun.eyepetizerkotlinstudy.utils.SharedPreferenceUtils

/**
 * Created by tang_xqing on 2019/12/4.
 */
class VideoCacheAdapter(ctx: Context, lists: MutableList<VideoTb>, rvView: RecyclerView) :
    BaseClickAdapter<VideoTb, VideoCacheAdapter.VideoCacheViewHolder>(ctx, lists, rvView) {

    fun setData(dataList: MutableList<VideoTb>) {
        mList.clear()
        mList.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoCacheViewHolder {
        var view =
            LayoutInflater.from(mCtx).inflate(R.layout.item_video_cache_layout, parent, false)
        view.setOnClickListener(this)
        return VideoCacheViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoCacheViewHolder, position: Int) {
        with(holder) {
            var data = mList.get(position).videoBean
            with(data) {
                GlideUtils.display(mCtx, feed as String, mIvImage)
                TextUtils.setText(mTvTitle, title as String)

                var key = SharedPreferenceUtils.getVideoDownloadStateByKey(playUrl as String)
                when (key) {
                    "start" -> {
                        mIvDownload.visibility = View.VISIBLE
                        TextUtils.setText(mTvStatus, "缓存中")
                    }
                    "finish" -> {
                        mIvDownload.visibility = View.GONE
                        TextUtils.setText(mTvStatus, "缓存完成")
                    }
                    else -> {
                        mIvDownload.visibility = View.VISIBLE
                        TextUtils.setText(mTvStatus, "未缓存")
                    }
                }
            }

            mIvDownload.setOnClickListener {
                // 点击开始缓存
                ToastUtils.showShortInfo("重新开始缓存")
                DownloadService.getInstance(mCtx, data.playUrl as String)
            }
        }
    }

    class VideoCacheViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        lateinit var mIvImage: ImageView
        lateinit var mTvTitle: TextView
        lateinit var mTvStatus: TextView
        lateinit var mIvDownload: ImageView

        init {
            itemView?.run {
                mIvImage = findViewById(R.id.iv_cach_image)
                mTvTitle = findViewById(R.id.tv_title)
                mTvStatus = findViewById(R.id.tv_status)
                mIvDownload = findViewById(R.id.iv_download)
            }
        }
    }
}