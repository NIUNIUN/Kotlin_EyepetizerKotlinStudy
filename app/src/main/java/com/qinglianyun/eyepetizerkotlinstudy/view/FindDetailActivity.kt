package com.qinglianyun.eyepetizerkotlinstudy.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.widget.Toolbar
import com.qinglianyun.base.adapter.BaseClickAdapter
import com.qinglianyun.base.utils.RecyclerViewUtils
import com.qinglianyun.base.utils.TextUtils
import com.qinglianyun.base.view.BaseActivity
import com.qinglianyun.eyepetizerkotlinstudy.R
import com.qinglianyun.eyepetizerkotlinstudy.adapter.HomeAdapter
import com.qinglianyun.eyepetizerkotlinstudy.presenter.FindDetailPresenter
import com.qinglianyun.eyepetizerkotlinstudy.view.i.IFindDetailView
import com.tt.lvruheng.eyepetizer.mvp.model.bean.FindBean
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean
import com.tt.lvruheng.eyepetizer.mvp.model.bean.VideoBean

/**
 * Created by tang_xqing on 2019/11/29.
 */
class FindDetailActivity : BaseActivity<IFindDetailView, FindDetailPresenter>(), IFindDetailView {

    private lateinit var mRvData: RecyclerView
    private lateinit var mTvDesc: TextView
    private lateinit var mToolbar: Toolbar

    private lateinit var mAdapter: HomeAdapter
    private var mDataList: MutableList<HomeBean.IssueListBean.ItemListBean> = mutableListOf()
    private var mCategory: FindBean? = null

    override fun getLayoutView(): Int {
        return R.layout.activity_find_detail
    }

    override fun initPresenter() {
        mPresenter = FindDetailPresenter(this)
    }

    override fun initViews() {
        mCategory = intent.getParcelableExtra<FindBean>(EXTRA_KEY)
        mRvData = findViewById(R.id.rv_find_detail)
        mTvDesc = findViewById(R.id.tv_find_detail_desc)
        mToolbar = findViewById(R.id.toolbar_find_detail)

        mCategory?.let {
            TextUtils.setText(mTvDesc, it.description as String)
            mToolbar.setTitle(it.name)
        }

        mAdapter = HomeAdapter(this, mDataList, mRvData)

        RecyclerViewUtils.initVerticalLayoutManager(mRvData, this)
        mRvData.adapter = mAdapter
    }

    override fun initListeners() {
        mAdapter.setRvClickListener(object :
            BaseClickAdapter.OnRvClickListener<HomeBean.IssueListBean.ItemListBean> {
            override fun onItemClick(
                view: View,
                data: HomeBean.IssueListBean.ItemListBean,
                position: Int
            ) {
                jumpToActivity(data)
            }
        })
    }

    override fun initData() {
        mCategory?.let {
            mPresenter.getCategoryDetail(it.id.toLong())
        }
    }

    private fun jumpToActivity(data: HomeBean.IssueListBean.ItemListBean) {
        data?.let {
            var dataBean = it.data
            dataBean?.run {
                var phono = cover?.feed
                var title = title
                var description = description
                var category = category
                var duration = duration
                var playUrl = playUrl
                var blurred = cover?.blurred
                var collect = consumption?.collectionCount
                var share = consumption?.shareCount
                var reply = consumption?.replyCount
                var videoBean: VideoBean = VideoBean(
                    phono,
                    title,
                    description,
                    duration,
                    playUrl,
                    category,
                    blurred,
                    collect,
                    share,
                    reply,
                    System.currentTimeMillis()
                )
                VideoDetailActivity.startAction(mActivity, videoBean)
            }
        }
    }

    override fun getCategoryDetailSuc(result: MutableList<HomeBean.IssueListBean.ItemListBean>) {
        mAdapter.setDataList(result)
    }

    override fun getCategoryDetailFail(code: Int, msg: String) {
    }

    companion object {
        const val EXTRA_KEY = "extra_key"
        fun startAction(ctx: Context, findBean: FindBean) {
            var intent = Intent(ctx, FindDetailActivity::class.java)
            intent.putExtra(EXTRA_KEY, findBean as Parcelable)
            ctx.startActivity(intent)
        }
    }
}