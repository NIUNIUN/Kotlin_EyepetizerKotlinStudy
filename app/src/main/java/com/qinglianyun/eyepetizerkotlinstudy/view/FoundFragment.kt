package com.qinglianyun.eyepetizerkotlinstudy.view

import android.support.v4.app.Fragment
import com.qinglianyun.base.view.BaseFragment
import com.qinglianyun.eyepetizerkotlinstudy.R
import com.qinglianyun.eyepetizerkotlinstudy.adapter.BasePagerAdater
import com.qinglianyun.eyepetizerkotlinstudy.presenter.FoundPresenter
import com.qinglianyun.eyepetizerkotlinstudy.view.i.IFoundView
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
            return FoundFragment()
        }
    }
}