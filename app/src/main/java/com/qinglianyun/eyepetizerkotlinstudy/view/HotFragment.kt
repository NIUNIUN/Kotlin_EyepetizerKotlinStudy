package com.qinglianyun.eyepetizerkotlinstudy.view

import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import com.qinglianyun.base.view.BaseFragment
import com.qinglianyun.eyepetizerkotlinstudy.R
import com.qinglianyun.eyepetizerkotlinstudy.adapter.HotPagerAdater
import com.qinglianyun.eyepetizerkotlinstudy.presenter.MainPresenter
import com.qinglianyun.eyepetizerkotlinstudy.view.i.IMainView

/**
 * Created by tang_xqing on 2019/11/22.
 */
class HotFragment : BaseFragment<IMainView, MainPresenter>(), IMainView {
    private lateinit var mVpList: ViewPager
    private lateinit var mTabLayout: TabLayout
    private lateinit var mPagerAdater: HotPagerAdater
    private var mDataList = arrayListOf<Fragment>()
    private var talTitle = arrayOf("周排行", "月排行", "总排行")

    override fun getLayoutId(): Int {
        return R.layout.fragment_hot
    }

    override fun initPresenter() {
        mPresenter = MainPresenter(this)
    }

    override fun initViews() {
        mTabLayout = mActivity.findViewById(R.id.tl_hot)
        mVpList = mActivity.findViewById(R.id.vp_hot_list)

        for (s in talTitle) {
            mTabLayout.addTab(mTabLayout.newTab().setText(s))
        }
        mTabLayout.setupWithViewPager(mVpList)

        mDataList.add(HotWeekFragment.getInstance())
        mDataList.add(HotMouthFragment.getInstance())
        mDataList.add(HotTotalFragment.getInstance())

        mPagerAdater = HotPagerAdater(childFragmentManager)
        mPagerAdater.setDataList(mDataList)
        mVpList.adapter = mPagerAdater
    }

    override fun initListeners() {
    }

    override fun initData() {
    }

    companion object {
        fun getInstance(): HotFragment {
            return HotFragment()
        }
    }
}