package com.qinglianyun.eyepetizerkotlinstudy.view

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.qinglianyun.base.adapter.BaseClickAdapter
import com.qinglianyun.base.utils.RecyclerViewUtils
import com.qinglianyun.base.view.BaseFragment
import com.qinglianyun.eyepetizerkotlinstudy.R
import com.qinglianyun.eyepetizerkotlinstudy.adapter.HomeAdapter
import com.qinglianyun.eyepetizerkotlinstudy.presenter.HomePresenter
import com.qinglianyun.eyepetizerkotlinstudy.view.i.IHomeView
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean
import com.tt.lvruheng.eyepetizer.mvp.model.bean.VideoBean
import org.jetbrains.anko.support.v4.share

/**
 * Created by tang_xqing on 2019/11/22.
 */
class HomeFragment : BaseFragment<IHomeView, HomePresenter>(), IHomeView {
    private lateinit var mRlList: RecyclerView
    private lateinit var mSrlHome: SwipeRefreshLayout
    private var mDataList: MutableList<HomeBean.IssueListBean.ItemListBean> = mutableListOf()
    private lateinit var mAdapter: HomeAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initPresenter() {
        mPresenter = HomePresenter(this)
    }

    override fun initViews() {
        mRlList = mActivity.findViewById(R.id.rl_home_list)
        mSrlHome = mActivity.findViewById(R.id.srl_home)

        mSrlHome.setColorSchemeResources(R.color.comm_color_main)
        RecyclerViewUtils.initVerticalLayoutManager(mRlList, mActivity)

        mAdapter = HomeAdapter(mActivity, mDataList, mRlList)
        mRlList.adapter = mAdapter
    }

    override fun initListeners() {
        mAdapter.setRvClickListener(object :
            BaseClickAdapter.OnRvClickListener<HomeBean.IssueListBean.ItemListBean> {
            override fun onItemClick(
                view: View,
                data: HomeBean.IssueListBean.ItemListBean,
                position: Int
            ) {
                Toast.makeText(mActivity, "点击 position = " + position, Toast.LENGTH_SHORT).show()
                jumpToVideoActivity(view,data?.data as HomeBean.IssueListBean.ItemListBean.DataBean)
            }
        })

        mSrlHome.setOnRefreshListener {
            mPresenter?.getHomeData()
        }
    }

    override fun initData() {
        mPresenter?.getHomeData()
    }

    override fun getDataSuccess(dataBean: HomeBean) {
        mSrlHome.isRefreshing = false
        var temp: MutableList<HomeBean.IssueListBean.ItemListBean> = mutableListOf()
        dataBean.issueList!!
            .flatMap { it.itemList!! }
            .filter { it.type.equals("video") }
            .forEach { temp.add(it) }

        mAdapter.setDataList(temp)
    }

    override fun getDataFail(code: Int, msg: String) {
        mSrlHome.isRefreshing = false
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show()
    }

    private fun jumpToVideoActivity(view: View,data: HomeBean.IssueListBean.ItemListBean.DataBean) {
        with(data) {
            var photo = cover?.feed
            var desc = description
            var duration = duration
            var playUrl = playUrl
            var blurred = cover?.blurred
            var share = consumption?.shareCount
            var collect = consumption?.collectionCount
            var reply = consumption?.replyCount
            var time = System.currentTimeMillis()
            var videoBean = VideoBean(
                photo,
                title,
                desc,
                duration,
                playUrl,
                category,
                blurred,
                collect,
                share,
                reply,
                time
            )
//            VideoDetailActivity.startAction(mActivity, videoBean)
            VideoDetailActivity.startAction(mActivity, videoBean,view)
        }
    }

    companion object {
        fun getInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}