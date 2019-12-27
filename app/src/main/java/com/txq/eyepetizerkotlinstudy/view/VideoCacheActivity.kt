package com.txq.eyepetizerkotlinstudy.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import com.txq.base.adapter.BaseClickAdapter
import com.txq.base.utils.RecyclerViewUtils
import com.txq.base.utils.TextUtils
import com.txq.base.view.BaseActivity
import com.txq.eyepetizerkotlinstudy.R
import com.txq.eyepetizerkotlinstudy.adapter.VideoCacheAdapter
import com.txq.eyepetizerkotlinstudy.bean.db.VideoTb
import com.txq.eyepetizerkotlinstudy.presenter.VideoCachePresenter
import com.txq.eyepetizerkotlinstudy.view.i.IVideoCacheView
import java.io.File

/**
 * Created by tang_xqing on 2019/12/4.
 */
class VideoCacheActivity : BaseActivity<IVideoCacheView, VideoCachePresenter>(), IVideoCacheView {
    private lateinit var mTbar: Toolbar
    private lateinit var mRvCache: RecyclerView

    private lateinit var mAdater: VideoCacheAdapter
    private var mDataList: MutableList<VideoTb> = mutableListOf()

    override fun getLayoutView(): Int {
        return R.layout.activity_video_cache
    }

    override fun initPresenter() {
        mPresenter = VideoCachePresenter(this)
    }

    override fun initViews() {

        mTbar = findViewById(R.id.tb_cache)
        mRvCache = findViewById(R.id.rv_video_cache)
        RecyclerViewUtils.initVerticalLayoutManager(mRvCache, this)

        mAdater = VideoCacheAdapter(this, mDataList, mRvCache)
        mRvCache.adapter = mAdater

        setSupportActionBar(mTbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun initListeners() {
        mTbar.setNavigationOnClickListener { finish() }
        mAdater.setRvClickListener(object : BaseClickAdapter.OnRvClickListener<VideoTb> {
            override fun onItemClick(view: View, data: VideoTb, position: Int) {
                if (TextUtils.isEmpty(data.filePath)) {
                    VideoDetailActivity.startAction(mActivity, data.videoBean)
                } else {
                    var fileUri = File(data.filePath)
                    var uri = Uri.fromFile(fileUri).toString()
                    Log.d("本地文件播放", " uri=$uri")
                    VideoDetailActivity.startAction(
                        mActivity,
                        data.videoBean, uri
                    )
                }
            }
        })
    }

    override fun initData() {
        mPresenter.getAllVideoCache(this)
    }

    override fun getAllVideoCacheSuc(result: MutableList<VideoTb>) {
        mAdater.setData(result)
    }

    override fun getAllVideoCacheFail(code: Int, msg: String) {
    }

    companion object {
        fun startAction(ctx: Context) {
            ctx.startActivity(Intent(ctx, VideoCacheActivity::class.java))
        }
    }
}