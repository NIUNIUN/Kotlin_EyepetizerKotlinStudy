package com.qinglianyun.eyepetizerkotlinstudy.view

import com.qinglianyun.base.view.BaseFragment
import com.qinglianyun.eyepetizerkotlinstudy.R
import com.qinglianyun.eyepetizerkotlinstudy.presenter.MainPresenter
import com.qinglianyun.eyepetizerkotlinstudy.view.i.IMainView

/**
 * Created by tang_xqing on 2019/11/22.
 */
class HotWeekFragment : BaseFragment<IMainView, MainPresenter>(), IMainView {
    override fun getLayoutId(): Int {
        return R.layout.fragment_hot_child
    }

    override fun initPresenter() {
    }

    override fun initViews() {
    }

    override fun initListeners() {
    }

    override fun initData() {
    }

    companion object {
        fun getInstance(): HotWeekFragment {
            return HotWeekFragment()
        }
    }
}