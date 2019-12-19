package com.qinglianyun.eyepetizerkotlinstudy.view

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import androidx.work.*
import com.qinglianyun.base.adapter.BaseClickAdapter
import com.qinglianyun.base.utils.RecyclerViewUtils
import com.qinglianyun.base.view.BaseFragment
import com.qinglianyun.eyepetizerkotlinstudy.R
import com.qinglianyun.eyepetizerkotlinstudy.adapter.HomeAdapter
import com.qinglianyun.eyepetizerkotlinstudy.adapter.HomePagedAdapter
import com.qinglianyun.eyepetizerkotlinstudy.presenter.HomePresenter
import com.qinglianyun.eyepetizerkotlinstudy.utils.*
import com.qinglianyun.eyepetizerkotlinstudy.view.i.IHomeView
import com.qinglianyun.eyepetizerkotlinstudy.viewmodels.HomeViewModel
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean
import com.tt.lvruheng.eyepetizer.mvp.model.bean.coverToVideoBean
import java.util.concurrent.TimeUnit

/**
 * Created by tang_xqing on 2019/11/22.
 */
class HomeFragment : BaseFragment<IHomeView, HomePresenter>(), IHomeView {
    private lateinit var mRlList: RecyclerView
    private lateinit var mSrlHome: SwipeRefreshLayout
    private var mDataList: MutableList<HomeBean.IssueListBean.ItemListBean> = mutableListOf()
    private lateinit var mAdapter: HomeAdapter

    private lateinit var mMAdapter: HomePagedAdapter

    private lateinit var mRegister: LifecycleRegistry

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initPresenter() {
        mPresenter = HomePresenter(this)
    }

    private lateinit var workRequest: WorkRequest

    override fun initViews() {
        mRlList = mActivity.findViewById(R.id.rl_home_list)
        mSrlHome = mActivity.findViewById(R.id.srl_home)

        mSrlHome.setColorSchemeResources(R.color.comm_color_main)
        RecyclerViewUtils.initVerticalLayoutManager(mRlList, mActivity)

        initPagingAdapter()

        requestWork()
    }

    private fun requestWork() {
        /**
         * 设置约束条件，只有当条件成立才会执行；条件不成立，代码运行也不会执行任务。
         */
        var constraint: Constraints = Constraints.Builder()
            .setRequiresCharging(true)  // 充电时请求
            .build()

        // OneTimeWorkRequestBuilder ：只执行一次
        workRequest = OneTimeWorkRequestBuilder<WorkManageUtil>()
            .setConstraints(constraint)
            .build()

        // PeriodicWorkRequest ：周期性执行任务
        var periodicWorkRequest =
            // 源码里面已经规定了最小时间间隔15分钟
            PeriodicWorkRequest.Builder(WorkManageUtil::class.java, 1, TimeUnit.SECONDS)
                .setConstraints(constraint)
                .build()

//        WorkManager.getInstance().enqueue(workRequest)
        WorkManager.getInstance().enqueue(periodicWorkRequest)

        var workA = OneTimeWorkRequestBuilder<WorkA>().build()
        var workB = OneTimeWorkRequestBuilder<WorkB>().build()
        /**
         * 以序列的形式执行任务。 其中一个任务执行失败，后面的任务不再执行
         */
        WorkManager.getInstance().beginWith(workRequest as OneTimeWorkRequest)
            .then(workA)
            .then(workB)
//            .enqueue()


        var workC = OneTimeWorkRequestBuilder<WorkC>().build()
        var workD = OneTimeWorkRequestBuilder<WorkD>().build()
        var chian1 = WorkManager.getInstance().beginWith(workC).then(workD)
        var chian2 = WorkManager.getInstance().beginWith(workRequest as OneTimeWorkRequest)

        var chian = mutableListOf<WorkContinuation>()
        chian.add(chian1)
        chian.add(chian2)

        /**
         * 以组合的形式执行任务。只保证每一个链顺序执行，不保证链与链之间任务顺序同步执行
         */
        WorkContinuation.combine(chian).then(workA).enqueue()

        /**
         * 给任务传递参数
         * setInputData --输入参数
         * getInputData --获取输入参数
         * setOutData --输出参数
         */
        var data = Data.Builder().putString("key","参数").build()
        var parameWorkD = OneTimeWorkRequestBuilder<WorkD>().setInputData(data).build()
        WorkManager.getInstance().enqueue(parameWorkD)
        // 没有找到如何获取任务输出参数
    }

    override fun initListeners() {
        mRegister = LifecycleRegistry(this)
        mRegister.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    private fun initAdapter() {
        mSrlHome.visibility = View.VISIBLE
        mAdapter = HomeAdapter(mActivity, mDataList, mRlList)
        mRlList.adapter = mAdapter

        mAdapter.setRvClickListener(object :
            BaseClickAdapter.OnRvClickListener<HomeBean.IssueListBean.ItemListBean> {
            override fun onItemClick(
                view: View,
                data: HomeBean.IssueListBean.ItemListBean,
                position: Int
            ) {
                jumpToVideoActivity(view, data)
            }
        })

        mPresenter?.getHomeData()

        mSrlHome.setOnRefreshListener {
            mPresenter?.getHomeData()
        }
    }

    private fun initPagingAdapter() {
        mSrlHome.setOnRefreshListener {
            mSrlHome.isRefreshing = false
        }
        mMAdapter = HomePagedAdapter(HomePagedAdapter.difCallback)
        mRlList.adapter = mMAdapter

        var viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.getPagedListLiveData()
            .observe(this, object : Observer<PagedList<HomeBean.IssueListBean.ItemListBean>> {
                override fun onChanged(t: PagedList<HomeBean.IssueListBean.ItemListBean>?) {
                    mMAdapter.submitList(t)
                }
            })

        mMAdapter.setOnClickListener { view, itemListBean ->
            jumpToVideoActivity(view, itemListBean)
        }
    }

    override fun initData() {

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

    private fun jumpToVideoActivity(view: View, data: HomeBean.IssueListBean.ItemListBean) {
        VideoDetailActivity.startAction(mActivity, data.coverToVideoBean(), view)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 取消任务。如果不取消任务，任务会一直执行直到
        WorkManager.getInstance().cancelWorkById(workRequest.id)
    }

    companion object {
        fun getInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}
