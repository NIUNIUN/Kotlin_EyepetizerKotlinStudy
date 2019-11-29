package com.qinglianyun.eyepetizerkotlinstudy.view

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.qinglianyun.base.adapter.BaseClickAdapter
import com.qinglianyun.base.utils.RecyclerViewGridDivier
import com.qinglianyun.base.utils.RecyclerViewUtils
import com.qinglianyun.base.view.BaseFragment
import com.qinglianyun.eyepetizerkotlinstudy.R
import com.qinglianyun.eyepetizerkotlinstudy.adapter.FoundAdapter
import com.qinglianyun.eyepetizerkotlinstudy.presenter.FoundPresenter
import com.qinglianyun.eyepetizerkotlinstudy.view.i.IFoundView
import com.tt.lvruheng.eyepetizer.mvp.model.bean.FindBean

/**
 * Created by tang_xqing on 2019/11/22.
 */
class FoundFragment : BaseFragment<IFoundView, FoundPresenter>(), IFoundView {
    private lateinit var mRlList: RecyclerView
    private lateinit var mAdapter: FoundAdapter
    private var mDataList: MutableList<FindBean> = mutableListOf()

    override fun getLayoutId(): Int {
        return R.layout.fragment_found
    }

    override fun initPresenter() {
        mPresenter = FoundPresenter(this)
    }

    override fun initViews() {
        mRlList = mActivity.findViewById(R.id.rl_found_list)
        RecyclerViewUtils.initGridLayoutManager(mRlList, mActivity, 2)
        mRlList.addItemDecoration(RecyclerViewGridDivier())
        mAdapter = FoundAdapter(mActivity, mDataList, mRlList)
        mRlList.adapter = mAdapter
    }

    override fun initListeners() {
        mAdapter.setRvClickListener(object : BaseClickAdapter.OnRvClickListener<FindBean> {
            override fun onItemClick(view: View, data: FindBean, position: Int) {
                Toast.makeText(mActivity, "点击 position = " + position, Toast.LENGTH_SHORT).show()
                FindDetailActivity.startAction(mActivity,data)
            }
        })
    }

    override fun initData() {
        mPresenter?.requestData()
    }

    override fun getDataSuccess(result: MutableList<FindBean>) {
        mAdapter.setData(result)
    }

    override fun getDataFail(code: Int, msg: String) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun getInstance(): FoundFragment {
            return FoundFragment()
        }
    }
}