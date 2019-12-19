package com.qinglianyun.eyepetizerkotlinstudy.view

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Parcelable
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v4.util.Pair
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.qinglianyun.base.utils.TextUtils
import com.qinglianyun.base.utils.ToastUtils
import com.qinglianyun.base.view.BaseActivity
import com.qinglianyun.eyepetizerkotlinstudy.R
import com.qinglianyun.eyepetizerkotlinstudy.bean.db.VideoDbManager
import com.qinglianyun.eyepetizerkotlinstudy.bean.db.VideoTb
import com.qinglianyun.eyepetizerkotlinstudy.presenter.VideoDetailPresenter
import com.qinglianyun.base.utils.GlideUtils
import com.qinglianyun.eyepetizerkotlinstudy.utils.SharedPreferenceUtils
import com.qinglianyun.eyepetizerkotlinstudy.view.i.IVideoDetailView
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.tt.lvruheng.eyepetizer.mvp.model.bean.VideoBean
import io.reactivex.schedulers.Schedulers

/**
 * Created by tang_xqing on 2019/11/26.
 */
class VideoDetailActivity : BaseActivity<IVideoDetailView, VideoDetailPresenter>(),
    IVideoDetailView {

    private lateinit var mGsyVideo: StandardGSYVideoPlayer
    private lateinit var mTvTitle: TextView
    private lateinit var mTvTime: TextView
    private lateinit var mTvDetail: TextView
    private lateinit var mTvFavorites: TextView
    private lateinit var mTvReply: TextView
    private lateinit var mTvShare: TextView
    private lateinit var mTvDownload: TextView

    private lateinit var mVideoBean: VideoBean
    private lateinit var mOritation: OrientationUtils

    private var isPlay: Boolean = false
    private var isPause: Boolean = true

    override fun getLayoutView(): Int {
        return R.layout.activity_video
    }

    override fun initPresenter() {
        mPresenter = VideoDetailPresenter(this)
    }

    override fun initViews() {
        mVideoBean = intent.getParcelableExtra(EXTRA_DATA)

        mGsyVideo = findViewById(R.id.sgsy_video)
        mTvTitle = findViewById(R.id.tv_video_title)
        mTvTime = findViewById(R.id.tv_video_time)
        mTvDetail = findViewById(R.id.tv_video_description)
        mTvFavorites = findViewById(R.id.tv_video_favorites)
        mTvShare = findViewById(R.id.tv_video_share)
        mTvReply = findViewById(R.id.tv_video_reply)
        mTvDownload = findViewById(R.id.tv_video_download)
    }

    override fun initListeners() {
        mTvDownload.setOnClickListener {
            // 点击前判断是否有权限
            var permission =
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (permission != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "请手动开启读取存储开权限", Toast.LENGTH_SHORT).show()
            } else {
                savaVideo()
            }
        }
    }

    private fun savaVideo() {
        mVideoBean.playUrl?.let {
            var key = SharedPreferenceUtils.getVideoDownloadStateByKey(it)
            if (TextUtils.isEmpty(key)) {
                Toast.makeText(this, "开始缓存", Toast.LENGTH_SHORT).show()
                SharedPreferenceUtils.putVideoDownloadState(it, "start")
//                var filePath = mPresenter.downloadFile(it)
                mPresenter.downloadFileToService(mActivity, it)

                var temp = VideoTb(mVideoBean.playUrl as String, mVideoBean, "")
                VideoDbManager.getInstance(this).addVideo(temp).subscribeOn(Schedulers.io())
                    .subscribe { }
            } else if ("finish".equals(key)) {
                ToastUtils.showShortInfo("该视频已经缓存过")
            } else {
                ToastUtils.showShortInfo("该视频已加入缓存队列了")
            }
        }
    }

    override fun initData() {
        mVideoBean.run {
            with(TextUtils) {
                setText(mTvTitle, title as String)
                setText(mTvDetail, description as String)
                setText(mTvFavorites, collect.toString())
                setText(mTvShare, share.toString())
                setText(mTvReply, reply.toString())
                var minue = duration?.div(60)
                var second = duration?.minus(minue?.times(60) as Long)
                var realMinue: String = if (minue!! < 10) "0$minue" else minue.toString()
                var realSecond: String = if (second!! < 10) "0$second" else second.toString()
                setText(mTvTime, "$category / $realMinue'$realSecond''")
            }
            prepateVideo()
        }
    }

    fun prepateVideo() {
        var uri = intent.getStringExtra(EXTRA_DATA_LOCALFILE)
        if (null != uri) {
            mGsyVideo.setUp(uri, false, mVideoBean.title)
        } else {
            // 设置播放地址
            mGsyVideo.setUp(mVideoBean.playUrl, true, mVideoBean.title)
        }

        // 返回按钮
        mGsyVideo.backButton.visibility = View.VISIBLE
        // 标题
        mGsyVideo.titleTextView.visibility = View.GONE
        mGsyVideo.isShowFullAnimation = true
        // 封面
        var iv = ImageView(this)
        iv.scaleType = ImageView.ScaleType.FIT_XY
        GlideUtils.display(this, mVideoBean?.feed as String, iv)
        mGsyVideo.thumbImageView = iv

        // 返回监听
        mGsyVideo.backButton.setOnClickListener { onBackPressed() }

        initOrientation()

        mGsyVideo.setVideoAllCallBack(object : GSYSampleCallBack() {
            override fun onPrepared(url: String?, vararg objects: Any?) {
                // 开始播放才能设置旋转和全部
                isPlay = true
                mOritation.isEnable = true
            }

            override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                // 退出全屏时
                mOritation.backToProtVideo()
            }
        })
        mGsyVideo.isNeedLockFull = true

        mGsyVideo.setLockClickListener { view, lock ->
            mOritation.isEnable = !lock
        }

        // 开始自动播放
//        autoPlayVideo()
    }

    private fun initOrientation() {
        // 旋转工具类
        mOritation = OrientationUtils(this, mGsyVideo)
        // 是否跟随系统。false--系统禁止旋转，也会旋转，因为内部特殊处理（监听旋转角度）
        mOritation.setRotateWithSystem(false)

        //设置旋转和全屏
        mOritation.isEnable = false

        // 点击全屏按钮监听
        mGsyVideo.fullscreenButton.setOnClickListener {
            // 直接横屏
            mOritation.resolveByClick()
            // 设置隐藏状态栏
            mGsyVideo.startWindowFullscreen(this, true, true)
        }


        /**
         * 1、横屏状态点击按钮，返回到竖屏状态，而不是横屏状态显示video+文字
         * 2、点击返回按钮，返回到竖屏状态，而不是直接返回
         * 问题1解决方法：设置startWindowFullscreen（），隐藏actionBar、statusBar
         * 问题2解决方法：监听返回按钮，设置backToProtVideo
         *
         */
    }

    /**
     * 自动播放
     */
    private fun autoPlayVideo() {
        mGsyVideo.startPlayLogic()    // 自动播放不需要ACCESS_NETWORK_STATE，点击播放需要ACCESS_NETWORK_STATE权限
    }

    override fun onBackPressed() {
        mOritation?.let {
            it.backToProtVideo()
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        isPause = true
        mGsyVideo.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        isPause = false
        mGsyVideo.onVideoResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isPlay) {
            mGsyVideo.currentPlayer.release()
        }

        mGsyVideo.setVideoAllCallBack(null)
        mGsyVideo.clearCurrentCache()

        mOritation.releaseListener()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        // 系统旋转
        if (isPlay && isPause) {
            mGsyVideo.onConfigurationChanged(this, newConfig, mOritation, true, true)
        }
    }

    companion object {
        const val EXTRA_DATA: String = "extra_video_bean"
        const val EXTRA_DATA_LOCALFILE: String = "extra_video_bean_local_file"
        const val TRANSITION_NMAE: String = "video_tranision_name"

        fun startAction(ctx: Context, videoBean: VideoBean) {
            var intent = Intent(ctx, VideoDetailActivity::class.java)
            intent.putExtra(EXTRA_DATA, videoBean as Parcelable)
            ctx.startActivity(intent)
        }

        fun startAction(ctx: Context, videoBean: VideoBean, uri: String) {
            var intent = Intent(ctx, VideoDetailActivity::class.java)
            intent.putExtra(EXTRA_DATA, videoBean as Parcelable)
            intent.putExtra(EXTRA_DATA_LOCALFILE, uri)
            ctx.startActivity(intent)
        }

        fun startAction(ctx: Context, videoBean: VideoBean, view: View) {
            var intent = Intent(ctx, VideoDetailActivity::class.java)
            intent.putExtra(EXTRA_DATA, videoBean as Parcelable)
            val pair = Pair(view, TRANSITION_NMAE)

            // 共享元素动画
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                ctx as Activity, pair
            )

            ctx.startActivity(intent, activityOptions.toBundle())
        }
    }
}