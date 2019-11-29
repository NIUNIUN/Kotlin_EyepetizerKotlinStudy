package com.qinglianyun.eyepetizerkotlinstudy.view

import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.widget.Toast
import com.qinglianyun.base.view.BaseFragment
import com.qinglianyun.eyepetizerkotlinstudy.R
import com.qinglianyun.eyepetizerkotlinstudy.adapter.BasePagerAdater
import com.qinglianyun.eyepetizerkotlinstudy.bean.TabInfoBean
import com.qinglianyun.eyepetizerkotlinstudy.presenter.HotPresenter
import com.qinglianyun.eyepetizerkotlinstudy.view.i.IHotView
import com.shuyu.gsyvideoplayer.video.GSYADVideoPlayer

/**
 * Created by tang_xqing on 2019/11/22.
 */
class HotFragment : BaseFragment<IHotView, HotPresenter>(), IHotView {

    private lateinit var mVpList: ViewPager
    private lateinit var mTabLayout: TabLayout
    private var mDataList = arrayListOf<Fragment>()
    private var talTitle = arrayListOf<String>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_hot
    }

    override fun initPresenter() {
        mPresenter = HotPresenter(this)
    }

    override fun initViews() {
        mTabLayout = mActivity.findViewById(R.id.tl_hot)
        mVpList = mActivity.findViewById(R.id.vp_hot_list)
    }

    override fun initListeners() {
    }

    override fun initData() {
        mPresenter?.getRankList()
    }

    override fun getDataListSuccess(result: TabInfoBean) {
        result?.run {
            result.tabInfo.tabList.mapTo(talTitle) { it.name }
            result.tabInfo.tabList.mapTo(mDataList) {
                when (it.name) {
                    "周排行" -> HotWeekFragment.getInstance(it.apiUrl)
                    "月排行" -> HotWeekFragment.getInstance(it.apiUrl)
                    "总排行" -> HotWeekFragment.getInstance(it.apiUrl)
                    else -> HotWeekFragment.getInstance(it.apiUrl)
                }
            }
            mVpList.adapter =
                BasePagerAdater(fragmentManager as FragmentManager, mDataList, talTitle)
            mTabLayout.setupWithViewPager(mVpList)
        }
    }

    override fun getDataListFail(code: Int, msg: String) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun getInstance(): HotFragment {
            return HotFragment()
        }
    }
    /**
     * 一直有个疑惑：
     *    ViewPager加载的Fragment，使用同Fragment不同实例，会出现当前所看到的Fragment内容与实际不一致，而且只有一个Fragment显示内容，其他Fragment界面空白。
     *    java、kotlin都会出现。但是kotlin，如果直接使用xml里控件id赋值，不会出现问题；通过id得到控件就会出现问题。
     *    解决方案：ViewPager加载的Fragment，布局文件要使用不用的控件id。
     *
     */
}