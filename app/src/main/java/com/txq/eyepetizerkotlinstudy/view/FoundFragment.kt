package com.txq.eyepetizerkotlinstudy.view

import android.support.v4.app.Fragment
import com.txq.base.utils.LogUtils
import com.txq.base.view.BaseFragment
import com.txq.eyepetizerkotlinstudy.R
import com.txq.eyepetizerkotlinstudy.adapter.BasePagerAdater
import com.txq.eyepetizerkotlinstudy.presenter.FoundPresenter
import com.txq.eyepetizerkotlinstudy.view.i.IFoundView
import kotlinx.android.synthetic.main.fragment_found.*

/**
 * Created by tang_xqing on 2019/12/11.
 */
class FoundFragment : BaseFragment<IFoundView, FoundPresenter>(), IFoundView {

    private val tabTitle = arrayListOf<String>("关注", "分类")
    private lateinit var mAdapter: BasePagerAdater

    override fun getLayoutId(): Int {
        return R.layout.fragment_found
    }

    override fun initPresenter() {
        mPresenter = FoundPresenter(this)
    }

    override fun initViews() {

        var list = arrayListOf<Fragment>()
        list.add(FollowFragment.getInstance())
        list.add(CategoryFragment.getInstance())
        mAdapter = BasePagerAdater(childFragmentManager, list, tabTitle)

        vp_found.adapter = mAdapter
        tbl_found.setupWithViewPager(vp_found)
    }

    override fun initListeners() {
    }

    override fun initData() {
    }

    companion object {
        fun getInstance(): FoundFragment {
            LogUtils.d("创建对象 FoundFragment")
            return FoundFragment()
        }
    }
}