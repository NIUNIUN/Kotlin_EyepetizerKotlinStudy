package com.qinglianyun.eyepetizerkotlinstudy.view

import android.support.v7.widget.RecyclerView
import com.qinglianyun.base.utils.RecyclerViewUtils
import com.qinglianyun.base.view.BaseFragment
import com.qinglianyun.eyepetizerkotlinstudy.R
import com.qinglianyun.eyepetizerkotlinstudy.presenter.MainPresenter
import com.qinglianyun.eyepetizerkotlinstudy.view.i.IMainView

/**
 * Created by tang_xqing on 2019/11/22.
 */
class FoundFragment : BaseFragment<IMainView, MainPresenter>(), IMainView {
    private lateinit var mRlList: RecyclerView

    override fun getLayoutId(): Int {
        return R.layout.fragment_found
    }

    override fun initPresenter() {
        mPresenter = MainPresenter(this)
    }

    override fun initViews() {
        mRlList = mActivity.findViewById(R.id.rl_found_list)

        RecyclerViewUtils.initVerticalLayoutManager(mRlList, mActivity)
    }

    override fun initListeners() {
    }

    override fun initData() {
    }

    companion object{
        fun getInstance() :FoundFragment{
            return FoundFragment()
        }
    }
}