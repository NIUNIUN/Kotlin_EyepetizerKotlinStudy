package com.qinglianyun.eyepetizerkotlinstudy.view


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.paging.PagedList
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.qinglianyun.base.utils.RecyclerViewUtils
import com.qinglianyun.base.utils.ToastUtils
import com.qinglianyun.base.view.BaseFragment
import com.qinglianyun.eyepetizerkotlinstudy.R
import com.qinglianyun.eyepetizerkotlinstudy.adapter.FollowAdapter
import com.qinglianyun.eyepetizerkotlinstudy.adapter.FollowPagingAdapter
import com.qinglianyun.eyepetizerkotlinstudy.presenter.FollowPresenter
import com.qinglianyun.eyepetizerkotlinstudy.view.i.IFollowView
import com.qinglianyun.eyepetizerkotlinstudy.viewmodels.FollowViewModel
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean
import com.tt.lvruheng.eyepetizer.mvp.model.bean.VideoBean
import kotlinx.android.synthetic.main.fragment_follow.*

/**
 * Created by tang_xqing on 2019/12/11.
 */
class FollowFragment : BaseFragment<IFollowView, FollowPresenter>(), IFollowView {

    private lateinit var mAdapter: FollowAdapter
    private lateinit var mMAdapter: FollowPagingAdapter
    private lateinit var mViewModel: FollowViewModel

    var mNextPageUrl: String? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_follow
    }

    override fun initPresenter() {
        mPresenter = FollowPresenter(this)
    }

    override fun initViews() {
        var list: MutableList<HomeBean.IssueListBean.ItemListBean> = mutableListOf()
        RecyclerViewUtils.initVerticalLayoutManager(rv_follow, mActivity)
        mAdapter = FollowAdapter(mActivity, list, rv_follow)

    }

    override fun initListeners() {

    }

    override fun initData() {
        // 两种方式加载更多数据
        initCustom()   // 方式一

//        initPaging()    // 方式二
    }

    /**
     * 通过判断RecycleView是否滑动到最后一个item，来加载更多的数据
     */
    fun initCustom() {
        rv_follow.adapter = mAdapter

        rv_follow.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                recyclerView?.run {
                    // 当前处于停止滑动
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        var visiblePosition =
                            (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                        var count = recyclerView.adapter.itemCount
                        if (visiblePosition == (count - 1)) {
                            // 加载更多数据
                            mNextPageUrl?.let {
                                mPresenter?.getFollowMoreData(it)
                            }
                        }
                    }
                }
            }
        })

        mPresenter?.getFollowData()
    }

    /**
     * 通过Paging库来加载更多数据
     */
    fun initPaging() {
        // 使用第三方库Paging加载更多数据
        mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(mActivity.application)
            .create(FollowViewModel::class.java)
        mMAdapter = FollowPagingAdapter(FollowPagingAdapter.diffCallback)
        rv_follow.adapter = mMAdapter

        mMAdapter.setFollowClickListener(object : FollowPagingAdapter.OnFollowClickListener {
            override fun onClickFollow(view: View, data: HomeBean.IssueListBean.ItemListBean) {
                ToastUtils.showShortInfo("关注")
            }

            override fun onClickChild(view: View, data: HomeBean.IssueListBean.ItemListBean) {
                jumpToVideo(view, data)
            }
        })

        mViewModel.getLivePageList()
            .observe(this, object : Observer<PagedList<HomeBean.IssueListBean.ItemListBean>> {
                override fun onChanged(t: PagedList<HomeBean.IssueListBean.ItemListBean>?) {
                    mMAdapter.submitList(t)
                }
            })
    }

    fun jumpToVideo(view: View, data: HomeBean.IssueListBean.ItemListBean) {
        // 转换成VideoBean
        data.data?.run {
            var feed = cover?.feed as String
            var blurred = cover?.blurred
            var shared = consumption?.shareCount
            var reply = consumption?.replyCount
            var collect = consumption?.collectionCount
            var videoBean: VideoBean = VideoBean(
                feed,
                title,
                description,
                duration,
                playUrl,
                category,
                blurred,
                collect,
                shared,
                reply,
                System.currentTimeMillis()
            )
            VideoDetailActivity.startAction(mActivity, videoBean, view)
        }
    }


    override fun getFollowDataSuc(result: HomeBean.IssueListBean) {
        result.itemList?.let {
            mNextPageUrl = result.nextPageUrl
            mAdapter.setDataList(it.toMutableList())
        }
    }

    override fun getFollowDataFail(code: Int, msg: String) {
    }

    companion object {
        fun getInstance(): FollowFragment {
            return FollowFragment()
        }
    }
}