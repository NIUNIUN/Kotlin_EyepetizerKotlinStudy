package com.qinglianyun.eyepetizerkotlinstudy.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Parcelable
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.qinglianyun.base.adapter.BaseClickAdapter
import com.qinglianyun.base.utils.BuildUtils
import com.qinglianyun.base.utils.RecyclerViewUtils
import com.qinglianyun.base.utils.TextUtils
import com.qinglianyun.base.view.BaseActivity
import com.qinglianyun.eyepetizerkotlinstudy.R
import com.qinglianyun.eyepetizerkotlinstudy.adapter.HomeAdapter
import com.qinglianyun.eyepetizerkotlinstudy.presenter.CategoryDetailPresenter
import com.qinglianyun.base.utils.GlideUtils
import com.qinglianyun.eyepetizerkotlinstudy.adapter.HomePagedAdapter
import com.qinglianyun.eyepetizerkotlinstudy.view.i.ICategoryDetailView
import com.qinglianyun.eyepetizerkotlinstudy.viewmodels.CategoryViewModel
import com.tt.lvruheng.eyepetizer.mvp.model.bean.CategoryBean
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean
import com.tt.lvruheng.eyepetizer.mvp.model.bean.coverToVideoBean

/**
 * Created by tang_xqing on 2019/11/29.
 */
class CategoryDetailActivity : BaseActivity<ICategoryDetailView, CategoryDetailPresenter>(),
    ICategoryDetailView {

    private lateinit var mRvData: RecyclerView
    private lateinit var mTvDesc: TextView
    private lateinit var mToolbar: Toolbar
    private lateinit var mCToolbar: CollapsingToolbarLayout
    private lateinit var mIvHeaderBg: ImageView

    private lateinit var mMAdater: HomePagedAdapter
    private lateinit var mAdapter: HomeAdapter
    private var mDataList: MutableList<HomeBean.IssueListBean.ItemListBean> = mutableListOf()
    private var mCategory: CategoryBean? = null

    override fun getLayoutView(): Int {
        return R.layout.activity_find_detail
    }

    override fun initPresenter() {
        mPresenter = CategoryDetailPresenter(this)
    }

    override fun initViews() {
        mCategory = intent.getParcelableExtra(EXTRA_KEY)
        mRvData = findViewById(R.id.rv_find_detail)
        mTvDesc = findViewById(R.id.tv_find_detail_desc)
        mToolbar = findViewById(R.id.toolbar_find_detail)
        mCToolbar = findViewById(R.id.ctool_find_detail)
        mIvHeaderBg = findViewById(R.id.iv_head_bg)

        setSupportActionBar(mToolbar)
        //  显示返回按钮
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mCToolbar.setExpandedTitleColor(Color.WHITE)        // 展开时的颜色

        if (BuildUtils.isMarshmallow()) {
            mCToolbar.setCollapsedTitleTextColor(getColor(R.color.comm_color_main_A0B61D)) // 折叠时的颜色
        } else {
            mCToolbar.setCollapsedTitleTextColor(Color.parseColor("FFA0B61D"))
        }

        RecyclerViewUtils.initVerticalLayoutManager(mRvData, this)
//        initAdapter()
        initPagedAdapter()
    }

    override fun initListeners() {
        mToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initAdapter() {
        mAdapter = HomeAdapter(this, mDataList, mRvData)
        mRvData.adapter = mAdapter

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
        mCategory?.let {
            mPresenter.getCategoryDetail(it.id.toLong())
        }
    }

    private fun initPagedAdapter() {
        mMAdater = HomePagedAdapter(HomePagedAdapter.difCallback)
        mMAdater.setOnClickListener { view, itemListBean ->
            jumpToActivity(itemListBean)
        }
        mRvData.adapter = mMAdater
        var mViewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)
        mViewModel.mCategoryId = mCategory?.id?.toLong() ?: -1L
        mViewModel.getCategoryLiveData()
            .observe(this, object : Observer<PagedList<HomeBean.IssueListBean.ItemListBean>> {
                override fun onChanged(t: PagedList<HomeBean.IssueListBean.ItemListBean>?) {
                    mMAdater.submitList(t)
                }
            })
    }

    override fun initData() {
        mCategory?.let {
            TextUtils.setText(mTvDesc, it.description as String)
            mCToolbar.setTitle(it.name)
            GlideUtils.display(this, it.bgPicture as String, mIvHeaderBg)
        }
    }

    private fun jumpToActivity(data: HomeBean.IssueListBean.ItemListBean) {
        VideoDetailActivity.startAction(mActivity, data.coverToVideoBean())
    }

    override fun getCategoryDetailSuc(result: MutableList<HomeBean.IssueListBean.ItemListBean>) {
        mAdapter.setDataList(result)
    }

    override fun getCategoryDetailFail(code: Int, msg: String) {
    }

    companion object {
        const val EXTRA_KEY = "extra_key"
        fun startAction(ctx: Context, categoryBean: CategoryBean) {
            var intent = Intent(ctx, CategoryDetailActivity::class.java)
            intent.putExtra(EXTRA_KEY, categoryBean as Parcelable)
            ctx.startActivity(intent)
        }
    }
}
